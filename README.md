# RecipeMenuManagement
Project implemented by using spring boot application
This application is designed using document first approach by using open speciation 3.0.

Build Downloaded project run, application starts with tomcat server

This project designed to manage the recipe menu list, requester can do CURD operations on recipe details.
User can filter recipes by using recipe name, recipe type, recipe ingredients and recipe instructions.

Request flows thru following layers to do persistence operations.
Controller layer
Service layer
Persistence layer

Swagger Documentation UI : http://localhost:9191/swagger-ui/index.html#/

API invocation object input documentation
-------------------------------------------
Invoke findAll api to see all the existing recipes.
using "veg", "nonveg" get the recipe details by using "http://localhost:9191/recipe/findByFoodType?foodType=veg"

Few basic Api urls, remaining you can get it swagger documentation.
http://localhost:9191/recipe/{id} : To get existing Recipe
http://localhost:9191/recipe/findAll : To get all recipes
Note : Pagination service implemented but not exposed in service
To insert new recipe invoke :http://localhost:9191/recipe url with below s
