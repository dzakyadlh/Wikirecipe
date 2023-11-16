package com.dzakyadlh.wikirecipe.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Detail : Screen("home/{id}") {
        fun createRoute(id: String) = "home/$id"
    }

    object SavedRecipe : Screen("saved_recipe")

    object About : Screen("about")
}
