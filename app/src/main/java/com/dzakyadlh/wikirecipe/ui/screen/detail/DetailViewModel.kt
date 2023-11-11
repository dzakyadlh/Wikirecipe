package com.dzakyadlh.wikirecipe.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dzakyadlh.wikirecipe.data.RecipeRepository
import com.dzakyadlh.wikirecipe.data.UiState
import com.dzakyadlh.wikirecipe.model.Recipe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: RecipeRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Recipe>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Recipe>>>
        get() = _uiState

    fun getRecipeById(id: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(listOf(repository.getRecipeById(id)))
        }
    }
}