package com.dzakyadlh.wikirecipe.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dzakyadlh.wikirecipe.data.RecipeRepository
import com.dzakyadlh.wikirecipe.ui.screen.detail.DetailViewModel
import com.dzakyadlh.wikirecipe.ui.screen.home.HomeViewModel

class ViewModelFactory(private val repository: RecipeRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}