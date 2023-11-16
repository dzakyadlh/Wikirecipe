package com.dzakyadlh.wikirecipe.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.dzakyadlh.wikirecipe.data.entity.SavedRecipe
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedRecipeDao {
    @Insert
    fun insert(savedRecipe: SavedRecipe)

    @Delete
    fun delete(savedRecipe: SavedRecipe)

    @Query("SELECT * from savedrecipe ORDER BY name ASC")
    fun getAllSavedRecipes(): Flow<List<SavedRecipe>>

    @Query("SELECT * FROM savedrecipe WHERE id = :id")
    fun getSavedRecipe(id: String): LiveData<SavedRecipe?>
}