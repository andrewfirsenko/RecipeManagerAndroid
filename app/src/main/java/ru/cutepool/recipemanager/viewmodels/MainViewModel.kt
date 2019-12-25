package ru.cutepool.recipemanager.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ru.cutepool.recipemanager.extensions.mutableLiveData
import ru.cutepool.recipemanager.models.data.RecipeIngredient
import ru.cutepool.recipemanager.models.data.RecipeItem
import ru.cutepool.recipemanager.models.data.RecipeStep
import ru.cutepool.recipemanager.repositories.RecipeRepository

class MainViewModel : ViewModel() {
    private val recipeRepository = RecipeRepository

    fun getRecipeData() : LiveData<List<RecipeItem>> {
        return mutableLiveData(loadRecipes())
    }

    fun getRecipeData(extraUrl: String) : LiveData<List<RecipeItem>> {
        return mutableLiveData(loadRecipes(extraUrl))
    }

    private fun loadRecipes(): List<RecipeItem> {
        return recipeRepository.loadRecipes()
    }

    private fun loadRecipes(extraUrl: String): List<RecipeItem> {
        return recipeRepository.loadRecipes(extraUrl)
    }

    fun getRecipeDataIngredients(extraUrl: String) : LiveData<List<RecipeIngredient>> {
        return mutableLiveData(loadRecipeIngredients(extraUrl))
    }

    private fun loadRecipeIngredients(extraUrl: String): List<RecipeIngredient> {
        return recipeRepository.loadRecipeIngredients(extraUrl)
    }

    fun getRecipeDataSteps(extraUrl: String) : LiveData<List<RecipeStep>> {
        return mutableLiveData(loadRecipeSteps(extraUrl))
    }

    private fun loadRecipeSteps(extraUrl: String): List<RecipeStep> {
        return recipeRepository.loadRecipeStreps(extraUrl)
    }

    fun getRecipeTitle(): String {
        return recipeRepository.getRecipeTitle()
    }
}