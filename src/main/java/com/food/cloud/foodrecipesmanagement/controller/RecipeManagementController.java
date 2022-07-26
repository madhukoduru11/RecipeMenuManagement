package com.food.cloud.foodrecipesmanagement.controller;

import com.food.cloud.api.RecipeApi;
import com.food.cloud.foodrecipesmanagement.service.RecipeManagementService;
import com.food.cloud.models.Recipe;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@RestController
public class RecipeManagementController implements RecipeApi {
    @Autowired
    RecipeManagementService recipeManagementService;

    @Override
    public ResponseEntity<Recipe> addRecipe(@Valid @RequestBody Recipe recipe){
        ResponseEntity<Recipe> responseEntity;
        Recipe addedRecipe =recipeManagementService.saveRecipe(recipe);
        if(addedRecipe ==null){
            responseEntity = responseEntity = new ResponseEntity<>(HttpStatus.CONFLICT);
        }else{
            responseEntity = new ResponseEntity<Recipe>(addedRecipe,HttpStatus.OK);
        }
        return responseEntity;
    }
    @Override
    public ResponseEntity<String> deleteRecipe(@PathVariable("recipeId") Long recipeId){
        String response = recipeManagementService.deleteRecipe(recipeId);
        ResponseEntity<String> responseEntity;

        if(response== null){
            responseEntity = new ResponseEntity<String>("InValid RecipeId",HttpStatus.NOT_FOUND);
        }else{
            responseEntity = new ResponseEntity<String>(response,HttpStatus.OK);
        }
        return responseEntity;
    }
    @Override
    public ResponseEntity<List<Recipe>> findRecipesByFoodType(@Valid @RequestParam(value = "foodType") String foodType){
        List<Recipe> recipes = recipeManagementService.getRecipeByFoodType(foodType);
        ResponseEntity responseEntity;
        if(null!=recipes){
            responseEntity= new ResponseEntity<List<Recipe>>(recipes, HttpStatus.OK);
        }else{
            List<Recipe> emptyRecipes = new ArrayList<Recipe>();
            responseEntity = new ResponseEntity<List<Recipe>>(emptyRecipes, HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }
    @Override
    public ResponseEntity<List<Recipe>> findRecipesByInstructionAndExcludedIngredient(@Valid @RequestParam(value = "text") String text
            ,@Valid @RequestParam(value = "ingredient") List<String> ingredient){
        List<Recipe> recipes = recipeManagementService.getRecipesByInstructionAndExcludedIngredient(text,ingredient);
        ResponseEntity responseEntity;
        if(null!=recipes){
            responseEntity= new ResponseEntity<List<Recipe>>(recipes, HttpStatus.OK);
        }else{
            List<Recipe> emptyRecipes = new ArrayList<Recipe>();
            responseEntity = new ResponseEntity<List<Recipe>>(emptyRecipes, HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }
    @Override
    public ResponseEntity<List<Recipe>> findRecipesByServingsAndIngredient(@Valid @RequestParam(value = "servings") Long servings
            ,@Valid @RequestParam(value = "ingredient") List<String> ingredient){
        List<Recipe> recipes = recipeManagementService.getRecipesByServingsAndIngredient(servings,ingredient);
        ResponseEntity responseEntity;
        if(null!=recipes){
            responseEntity= new ResponseEntity<List<Recipe>>(recipes, HttpStatus.OK);
        }else{
            List<Recipe> emptyRecipes = new ArrayList<Recipe>();
            responseEntity = new ResponseEntity<List<Recipe>>(emptyRecipes, HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }
    @Override
    public ResponseEntity<Recipe> getRecipeById(@PathVariable("recipeId") Long recipeId){
        Recipe recipe =recipeManagementService.getRecipeById(recipeId);
        ResponseEntity<Recipe> responseEntity;
        if(recipe==null){
            recipe = new Recipe();
            responseEntity= new ResponseEntity<Recipe>(recipe,HttpStatus.NOT_FOUND);
        }else{
            responseEntity= new ResponseEntity<Recipe>(recipe,HttpStatus.OK);
        }
        return responseEntity;
    }
    @Override
    public ResponseEntity<Recipe> updateRecipe(@Valid @RequestBody Recipe body){
        Recipe recipe = recipeManagementService.updateRecipe(body);
        ResponseEntity<Recipe> responseEntity;
        if(recipe==null){
            recipe = new Recipe();
            responseEntity= new ResponseEntity<Recipe>(recipe,HttpStatus.NOT_FOUND);
        }else{
            responseEntity= new ResponseEntity<Recipe>(recipe,HttpStatus.OK);
        }
        return responseEntity;
    }
    @Override
    public ResponseEntity<List<Recipe>> findAll(){
        List<Recipe> listOfRecipes = recipeManagementService.getAllRecipes();
        ResponseEntity<List<Recipe>> responseEntity;
        if(listOfRecipes==null){
            responseEntity =  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            responseEntity=new ResponseEntity<List<Recipe>>(listOfRecipes,HttpStatus.OK);
        }
        return responseEntity;
    }
}
