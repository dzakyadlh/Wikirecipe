package com.dzakyadlh.wikirecipe.di

import com.dzakyadlh.wikirecipe.data.RecipeRepository

object Injection {
    fun provideRepository(): RecipeRepository {
        return RecipeRepository.getInstance()
    }
}