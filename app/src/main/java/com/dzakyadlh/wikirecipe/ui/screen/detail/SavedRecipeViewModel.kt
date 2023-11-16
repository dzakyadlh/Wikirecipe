package com.dzakyadlh.wikirecipe.ui.screen.detail

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dzakyadlh.wikirecipe.data.SavedRecipeRepository
import com.dzakyadlh.wikirecipe.data.UiState
import com.dzakyadlh.wikirecipe.data.entity.SavedRecipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class SavedRecipeViewModel(context: Context) : ViewModel() {
    private val mSavedRecipeRepository: SavedRecipeRepository = SavedRecipeRepository(context)
    private val _uiState: MutableStateFlow<UiState<List<SavedRecipe>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: MutableStateFlow<UiState<List<SavedRecipe>>>
        get() = _uiState

    fun insert(savedRecipe: SavedRecipe) {
        mSavedRecipeRepository.insert(savedRecipe)
    }

    fun delete(savedRecipe: SavedRecipe) {
        mSavedRecipeRepository.delete(savedRecipe)
    }

    fun getAllSavedRecipes(): Flow<List<SavedRecipe>> {
        return mSavedRecipeRepository.getAllSavedRecipes()
    }

    fun getSavedRecipe(id: String): LiveData<SavedRecipe?> {
        return mSavedRecipeRepository.getSavedRecipe(id)
    }
}