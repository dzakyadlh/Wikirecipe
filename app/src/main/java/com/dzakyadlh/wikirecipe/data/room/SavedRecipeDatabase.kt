package com.dzakyadlh.wikirecipe.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dzakyadlh.wikirecipe.data.entity.SavedRecipe

@Database(entities = [SavedRecipe::class], version = 1)
abstract class SavedRecipeDatabase : RoomDatabase() {
    abstract fun savedRecipeDao(): SavedRecipeDao

    companion object {
        @Volatile
        private var INSTANCE: SavedRecipeDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): SavedRecipeDatabase {
            if (INSTANCE == null) {
                synchronized(SavedRecipeDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        SavedRecipeDatabase::class.java,
                        "saved_recipe_database"
                    ).build()
                }
            }
            return INSTANCE as SavedRecipeDatabase
        }
    }
}