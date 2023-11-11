package com.dzakyadlh.wikirecipe.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Detail : Screen("home/{id}") {
        fun createRoute(id: String) = "home/$id"
    }

    object About : Screen("about")
}
