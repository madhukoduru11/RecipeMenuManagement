package com.food.cloud.foodrecipesmanagement.service;

import com.food.cloud.foodrecipesmanagement.entity.RecipeDetails;
import com.food.cloud.foodrecipesmanagement.repository.RecipeManagementRepository;
import com.food.cloud.foodrecipesmanagement.utility.ApiHelper;
import com.food.cloud.models.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class RecipeManagementService {

    @Autowired
    private RecipeManagementRepository recipeRepository;
    @Autowired
    ApiHelper apiHelper;
    @Autowired
    ResourceLoader resourceLoader;

    public Recipe saveRecipe(Recipe recipe) {
        RecipeDetails recipeDetails = apiHelper.objectResourceToEntityCopy(recipe);
        RecipeDetails savedRecipeDetails = recipeRepository.save(recipeDetails);
        return apiHelper.objectEntityCopyToResourceCopy(savedRecipeDetails);
    }

    public List<Recipe> saveRecipes(List<Recipe> recipes) {
        List<RecipeDetails> recipeDetailsList = apiHelper.objectResourceListToEntityListCopy(recipes);
        List<Recipe> recipesList = apiHelper.objectEntityListToResourceListCopy(recipeRepository.saveAll(recipeDetailsList));
        return recipesList;
    }

/*    public List<Recipe> getRecipes() {
        return apiHelper.objectEntityListToResourceListCopy(recipeRepository.findAll());
    }*/

    public List<Recipe> getRecipeByFoodType(String recipeType) {
        List<RecipeDetails> recipeDetailsList = recipeRepository.findByFoodType(recipeType);
        List<Recipe> recipeList=null;
        if(recipeDetailsList.size()> 0){
            recipeList = apiHelper.objectEntityListToResourceListCopy(recipeDetailsList);
        }
        return recipeList;
    }

    public Recipe getRecipeByName(String name) {
        return recipeRepository.findByName(name);
    }

    public String deleteRecipe(Long id) {
        recipeRepository.deleteById(id);
        return "product removed !!"  + id;
    }

    public Recipe updateRecipe(Recipe recipe) {
        RecipeDetails  recipeDetails =apiHelper.objectResourceToEntityCopy(recipe);
        RecipeDetails existingRecipe = recipeRepository.findById(recipeDetails.getId()).orElse(null);
        Recipe persis_recipe=null;
        if(!(null==existingRecipe)){
            existingRecipe.setName(recipe.getName());
            existingRecipe.setFoodType(recipe.getFoodType());
            existingRecipe.setNumOfServings(recipe.getNumOfServings());
            existingRecipe.setIngredients(recipe.getIngredients());
            existingRecipe.setInstructions(recipe.getInstructions());
            persis_recipe=  apiHelper.objectEntityCopyToResourceCopy(recipeRepository.save(existingRecipe));
        }
        return persis_recipe;
    }

    /*public List<Recipe> getRecipesMultipleSelection(String disType, Integer servings, String inclRecipes, String exclRecipes) {

        List<Recipe> recipes=recipeRepository.findByDisTypeAndServings(disType, servings);

        List<Recipe> inclueRecipes= recipes.stream().filter(recipe -> recipe.getIngredients()
                .contains(inclRecipes)).collect(Collectors.toList());

        List<Recipe> excludeRecipes = inclueRecipes.stream().filter(recipe -> !recipe.getIngredients()
                .contains(exclRecipes)).collect(Collectors.toList());
        return excludeRecipes;
    }*/

    public List<Recipe> getRecipesByInstructionAndExcludedIngredient(String instruction, List<String> ingredients){

        List<RecipeDetails> recipeDetailsList = recipeRepository.findByInstructionsContaining(instruction);
        List<Recipe> excludedIngredientRecipe=null;
        if(recipeDetailsList.size()>0){
            List<Recipe> instructionRecipe = apiHelper.objectEntityListToResourceListCopy(recipeDetailsList);
            excludedIngredientRecipe = instructionRecipe.stream().filter(recipe -> !recipe.getIngredients()
                    .containsAll(ingredients)).collect(Collectors.toList());
        }
        return excludedIngredientRecipe;
    }

    public List<Recipe> getRecipesByServingsAndIngredient(Long noOfServings,List<String> ingredients){
        List<RecipeDetails> recipeDetailsList = recipeRepository.findByNumOfServings(noOfServings);
        recipeDetailsList.stream().forEach(System.out::println);
        List<RecipeDetails> ingredientRecipes= recipeDetailsList.stream().filter(recipe -> recipe.getIngredients()
                .containsAll(ingredients)).collect(Collectors.toList());
        List<Recipe> instructionRecipe=null;
        if(!(null==ingredientRecipes) && ingredientRecipes.size()>0){
            instructionRecipe=apiHelper.objectEntityListToResourceListCopy(ingredientRecipes);
        }
        return instructionRecipe;
    }

    public Recipe getRecipeById(Long recipeId){
        RecipeDetails  recipeDetails = recipeRepository.findById(recipeId).orElse(null);
        Recipe recipe=null;
        if(!(null==recipeDetails)){
            recipe=apiHelper.objectEntityCopyToResourceCopy(recipeDetails);
        }
        return recipe;
    }

    public List<Recipe> getAllRecipes(){
        List<RecipeDetails>  recipeDetails = recipeRepository.findAll();
        List<Recipe> listRecipe=null;
        if(null!=recipeDetails && recipeDetails.size() > 0){
            listRecipe = apiHelper.objectEntityListToResourceListCopy(recipeDetails);
        }
        return listRecipe;
    }
    /*
     To Retrieve per page requested Recipes objects
    * */
    public List<Recipe> getAllRecipes(int offset, int pageSize){
        Page<RecipeDetails>  recipeDetails = recipeRepository.findAll(PageRequest.of(offset, pageSize));
        List<RecipeDetails> recipeList=recipeDetails.get().collect(Collectors.toList());
        List<Recipe> listRecipe=null;
        if(null!=recipeList && recipeList.size() > 0){
            listRecipe = apiHelper.objectEntityListToResourceListCopy(recipeList);
        }
        return listRecipe;
    }
   @PostConstruct
    public void loadApplicationData(){
        Resource resource = resourceLoader.getResource("classpath:"+"csv/upload.csv");
        try{
            List<RecipeDetails>  recipeDetails=ApiHelper.loadCSVFileData(resource.getInputStream());
            recipeRepository.saveAll(recipeDetails);
            //recipeDetails.stream().flatMap(t->t.getIngredients().stream()).forEach(System.out::println);
        }catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file:"  + e.getMessage());
        }
    }

    //public void findAllProductsWithPagination
}
