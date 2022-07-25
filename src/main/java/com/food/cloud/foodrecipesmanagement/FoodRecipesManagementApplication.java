package com.food.cloud.foodrecipesmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(
		basePackages = {
				"com.food.cloud",
				"com.food.cloud.api",
				"com.food.cloud.models",
		})
public class FoodRecipesManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodRecipesManagementApplication.class, args);
	}

}
