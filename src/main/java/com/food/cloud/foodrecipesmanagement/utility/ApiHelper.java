package com.food.cloud.foodrecipesmanagement.utility;

import com.food.cloud.foodrecipesmanagement.entity.RecipeDetails;
import com.food.cloud.models.Recipe;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Component
public class ApiHelper {

    public RecipeDetails objectResourceToEntityCopy(Recipe recipe){
        RecipeDetails recipeDetails= new RecipeDetails();
        recipeDetails .setId(recipe.getId());
        recipeDetails.setName(recipe.getName());
        recipeDetails.setFoodType(recipe.getFoodType());
        recipeDetails.setNumOfServings(recipe.getNumOfServings());
        recipeDetails.setInstructions(recipe.getInstructions());
        recipeDetails.setIngredients(recipe.getIngredients());
        return recipeDetails;
    }

    public Recipe objectEntityCopyToResourceCopy(RecipeDetails recipeDetails){
        Recipe recipe= new Recipe();
        recipe.setId(recipeDetails.getId());
        recipe.setName(recipeDetails.getName());
        recipe.setFoodType(recipeDetails.getFoodType());
        recipe.setNumOfServings(recipeDetails.getNumOfServings());
        recipe.setInstructions(recipeDetails.getInstructions());
        recipe.setIngredients(recipeDetails.getIngredients());
        return recipe;
    }

    public List<RecipeDetails> objectResourceListToEntityListCopy(List<Recipe> listRecipes){
        List<RecipeDetails> listRecipeDetails= new ArrayList<RecipeDetails>();
        for(Recipe  recipes : listRecipes) {
            listRecipeDetails.add(objectResourceToEntityCopy(recipes));
        }
        return listRecipeDetails;
    }

    public List<Recipe> objectEntityListToResourceListCopy(List<RecipeDetails> listRecipesDetails){
        List<Recipe> listRecipe= new ArrayList<Recipe>();
        for(RecipeDetails recipeDetails : listRecipesDetails) {
            listRecipe.add(objectEntityCopyToResourceCopy(recipeDetails));
        }
        return listRecipe;
    }

    public static List<RecipeDetails>  loadCSVFileData(InputStream is){

        HashMap map = new HashMap();


        try {

            BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
            List<RecipeDetails> recipeDetailsList = new ArrayList<RecipeDetails>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                RecipeDetails recipeDetail = new RecipeDetails();
                        recipeDetail.setName(csvRecord.get("name").strip());
                        recipeDetail.setFoodType(csvRecord.get("foodType"));
                        recipeDetail.setInstructions(csvRecord.get("Instructions"));
                        recipeDetail.setNumOfServings(Long.parseLong(csvRecord.get("numberOfServings")));
                            recipeDetail.setIngredients(Arrays.asList(csvRecord.get("ingredients").split(":")));
                        recipeDetailsList.add(recipeDetail);
            }
            return recipeDetailsList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file:"  + e.getMessage());
        }
    }
}
