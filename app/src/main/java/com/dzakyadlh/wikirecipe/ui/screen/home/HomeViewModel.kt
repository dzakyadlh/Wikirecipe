package com.dzakyadlh.wikirecipe.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dzakyadlh.wikirecipe.data.RecipeRepository
import com.dzakyadlh.wikirecipe.data.UiState
import com.dzakyadlh.wikirecipe.model.Recipe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: RecipeRepository) : ViewModel() {

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

//    fun search(newQuery:String) {
//        _query.value = newQuery
//        _sortedRecipes.value = repository.searchRecipes(_query.value)
//            .sortedBy { it.name }
//            .groupBy { it.name[0] }
//    }

    private val _uiState: MutableStateFlow<UiState<List<Recipe>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Recipe>>>
        get() = _uiState

    fun getRecipes() {
        viewModelScope.launch {
            repository.getRecipes()
                .catch {
                _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { recipe ->
                    _uiState.value = UiState.Success(recipe)
                }
        }
    }
}