package ru.cutepool.recipemanager.repositories


import org.json.JSONException
import org.json.JSONObject
import ru.cutepool.recipemanager.SendDoGet
import ru.cutepool.recipemanager.models.data.RecipeIngredient
import ru.cutepool.recipemanager.models.data.RecipeItem
import ru.cutepool.recipemanager.models.data.RecipeStep

object RecipeRepository {

    private lateinit var recipeTitle: String

    fun loadRecipes() : List<RecipeItem> {
        //TODO загрузка рецептов из интернета
        val recipes = mutableListOf<RecipeItem>()

        recipes.add(RecipeItem("1", "Что-то", "default", "BREAKFAST"))
        recipes.add(RecipeItem("2", "Что-то", "default", "LUNCH"))


        return recipes
    }

    fun loadRecipes(extraUrl: String) : List<RecipeItem> {
        val recipes = mutableListOf<RecipeItem>()
        val jsonResponse = SendDoGet().execute(extraUrl).get()

        try {
            val jsonObject = JSONObject(jsonResponse)
            val jsonArray = jsonObject.getJSONArray("recipes")
            for (i in 0 until jsonArray.length()) {
                val jsonRecipe = jsonArray.getJSONObject(i)
                recipes.add(RecipeItem( jsonRecipe.get("id").toString().trim(),
                                        jsonRecipe.get("name").toString().trim(),
                                        jsonRecipe.get("image").toString().trim(),
                                        jsonRecipe.get("category").toString().trim()))
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return recipes
    }

    fun loadRecipeIngredients(extraUrl: String): List<RecipeIngredient> {
        val ingredients = mutableListOf<RecipeIngredient>()
        val jsonResponse = SendDoGet().execute(extraUrl).get()
        try {
            val jsonObject = JSONObject(jsonResponse)
            // получаем title
            recipeTitle = jsonObject.getString("name").toString().trim()
            val jsonArray = jsonObject.getJSONArray("ingredients")
            for (i in 0 until jsonArray.length()) {
                val jsonIngredient = jsonArray.getJSONObject(i)
                ingredients.add(
                    RecipeIngredient(
                    name = jsonIngredient.get("name").toString().trim(),
                    quantity = jsonIngredient.get("quantity").toString().trim()))
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return ingredients
    }

    fun loadRecipeStreps(extraUrl: String): List<RecipeStep> {
        val steps = mutableListOf<RecipeStep>()
        val jsonResponse = SendDoGet().execute(extraUrl).get()
        try {
            val jsonObject = JSONObject(jsonResponse)
            val jsonArray = jsonObject.getJSONArray("steps")
            for (i in 0 until jsonArray.length()) {
                val jsonStep = jsonArray.getJSONObject(i)
                steps.add(
                    RecipeStep(
                        action = jsonStep.get("action").toString().trim(),
                        image = jsonStep.get("image").toString().trim()))
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return steps
    }

    fun getRecipeTitle(): String {
        return recipeTitle
    }
}