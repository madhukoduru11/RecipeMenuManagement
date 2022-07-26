package com.food.cloud.foodrecipesmanagement.repository;

import com.food.cloud.foodrecipesmanagement.entity.RecipeDetails;
import com.food.cloud.models.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RecipeManagementRepository extends JpaRepository<RecipeDetails,Long> {
    RecipeDetails findByName(String recipeName);

    // findById(Long id);
    List<RecipeDetails> findByFoodType(String dishType);
    List<RecipeDetails> findByNumOfServings(Long dishType);
    List<RecipeDetails> findByInstructionsContaining(String instructions);
    //@Query(SELECT re FROM Recipe re WHERE re.dishType= ?1 and re.servings= ?2)
    //List<Recipe> findByDisTypeAndServings(String disType, Integer servings);
}
