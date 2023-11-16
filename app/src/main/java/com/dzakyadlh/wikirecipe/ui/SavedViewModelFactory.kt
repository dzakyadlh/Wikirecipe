package com.dzakyadlh.wikirecipe.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dzakyadlh.wikirecipe.ui.screen.detail.SavedRecipeViewModel
import java.lang.ref.WeakReference

class SavedViewModelFactory private constructor(private val context:Context) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SavedRecipeViewModel::class.java)) {
            return SavedRecipeViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

    companion object {
        private var INSTANCE: WeakReference<SavedViewModelFactory>? = null

        @JvmStatic
        fun getInstance(context: Context): SavedViewModelFactory {
            if (INSTANCE == null || INSTANCE?.get() == null) {
                synchronized(SavedViewModelFactory::class.java) {
                    INSTANCE = WeakReference(SavedViewModelFactory(context))
                }
            }
            return INSTANCE!!.get()!!
        }
    }
}