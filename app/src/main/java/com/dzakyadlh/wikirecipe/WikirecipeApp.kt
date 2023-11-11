package com.dzakyadlh.wikirecipe

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dzakyadlh.wikirecipe.ui.navigation.Screen
import com.dzakyadlh.wikirecipe.ui.screen.detail.DetailScreen
import com.dzakyadlh.wikirecipe.ui.screen.home.HomeScreen
import com.dzakyadlh.wikirecipe.ui.theme.WikirecipeTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WikirecipeApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(navigateToDetail = { id ->
                navController.navigate(
                    Screen.Detail.createRoute(id)
                )
            })
        }
        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) {
            val id = it.arguments?.getString("id") ?: -1L
            DetailScreen(id = id.toString(), navigateBack = { navController.navigateUp() })
        }
        composable(Screen.About.route) {
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WikirecipeAppPreview() {
    WikirecipeTheme {
        WikirecipeApp()
    }
}