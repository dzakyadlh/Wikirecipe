package com.dzakyadlh.wikirecipe.data

import android.content.Context
import androidx.lifecycle.LiveData
import com.dzakyadlh.wikirecipe.data.entity.SavedRecipe
import com.dzakyadlh.wikirecipe.data.room.SavedRecipeDao
import com.dzakyadlh.wikirecipe.data.room.SavedRecipeDatabase
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class SavedRecipeRepository(context: Context) {
    private val mSavedRecipeDao: SavedRecipeDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = SavedRecipeDatabase.getDatabase(context)
        mSavedRecipeDao = db.savedRecipeDao()
    }

    fun getAllSavedRecipes(): Flow<List<SavedRecipe>> {
        return mSavedRecipeDao.getAllSavedRecipes()
    }

    fun getSavedRecipe(id: String): LiveData<SavedRecipe?> {
        return mSavedRecipeDao.getSavedRecipe(id)
    }

    fun insert(savedRecipe: SavedRecipe) {
        executorService.execute { mSavedRecipeDao.insert(savedRecipe) }
    }

    fun delete(savedRecipe: SavedRecipe) {
        executorService.execute { mSavedRecipeDao.delete(savedRecipe) }
    }
}