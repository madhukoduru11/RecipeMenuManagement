package com.food.cloud.foodrecipesmanagement.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
@AllArgsConstructor
@Builder
@Entity
@Table(name = "RECIPE_DETAILS")
public class RecipeDetails {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String foodType;
    @ElementCollection
    private List<String> ingredients;
    private Long numOfServings;
    private String instructions;
    public RecipeDetails(){

    }
    public RecipeDetails(String name,String foodTyp,List<String> ingredients,String instructions,Long numOfServings){
       this.name=name;
       this.foodType=foodTyp;
       this.ingredients=ingredients;
       this.instructions=instructions;
       this.numOfServings=numOfServings;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<String> getIngredients() {
        return ingredients;
    }
    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
    public Long getNumOfServings() {
        return numOfServings;
    }
    public void setNumOfServings(Long numOfServings) {
        this.numOfServings = numOfServings;
    }
    public String getInstructions() {
        return instructions;
    }
    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
    public String getFoodType() {
        return foodType;
    }
    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }


}
