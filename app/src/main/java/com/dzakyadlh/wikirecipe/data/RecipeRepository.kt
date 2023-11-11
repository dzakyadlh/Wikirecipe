package com.dzakyadlh.wikirecipe.data

import com.dzakyadlh.wikirecipe.model.Recipe
import com.dzakyadlh.wikirecipe.model.RecipeData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class RecipeRepository {

    private val recipe = mutableListOf<Recipe>()

    init {
        if (recipe.isEmpty()) {
            RecipeData.recipes.forEach {
                recipe.add(Recipe(it.id, it.name, it.description, it.photoUrl))
            }
        }
    }

    fun getRecipes(): Flow<List<Recipe>> {
        return flowOf(recipe)
    }

    fun getRecipeById(id: String): Recipe {
        return recipe.first {
            it.id == id
        }
    }

    fun searchRecipes(query: String): List<Recipe> {
        return RecipeData.recipes.filter {
            it.name.contains(query, ignoreCase = true)
        }
    }

    companion object {
        @Volatile
        private var instance: RecipeRepository? = null

        fun getInstance(): RecipeRepository = instance ?: synchronized(this) {
            RecipeRepository().apply {
                instance = this
            }
        }
    }
}