package com.dzakyadlh.wikirecipe.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dzakyadlh.wikirecipe.data.RecipeRepository
import com.dzakyadlh.wikirecipe.model.Recipe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class WikirecipeViewModel(private val repository: RecipeRepository): ViewModel() {
    private val _sortedRecipes = MutableStateFlow(
        repository.getRecipes().sortedBy { it.name }.groupBy { it.name[0] }
    )

    val sortedRecipes: StateFlow<Map<Char, List<Recipe>>> get() = _sortedRecipes

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun search(newQuery:String) {
        _query.value = newQuery
        _sortedRecipes.value = repository.searchRecipes(_query.value)
            .sortedBy { it.name }
            .groupBy { it.name[0] }
    }
}

class ViewModelFactory(private val repository: RecipeRepository): ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WikirecipeViewModel::class.java)){
            return WikirecipeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}