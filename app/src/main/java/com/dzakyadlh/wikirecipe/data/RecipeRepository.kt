package com.dzakyadlh.wikirecipe.data

import com.dzakyadlh.wikirecipe.model.Recipe
import com.dzakyadlh.wikirecipe.model.RecipeData

class RecipeRepository {
    fun getRecipes():List<Recipe> {
        return RecipeData.recipes
    }

    fun searchRecipes(query:String):List<Recipe>{
        return RecipeData.recipes.filter {
            it.name.contains(query,ignoreCase = true)
        }
    }
}