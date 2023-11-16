package com.dzakyadlh.wikirecipe

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dzakyadlh.wikirecipe.ui.navigation.Screen
import com.dzakyadlh.wikirecipe.ui.screen.about.AboutScreen
import com.dzakyadlh.wikirecipe.ui.screen.detail.DetailScreen
import com.dzakyadlh.wikirecipe.ui.screen.home.HomeScreen
import com.dzakyadlh.wikirecipe.ui.screen.savedrecipe.SavedRecipeScreen
import com.dzakyadlh.wikirecipe.ui.theme.WikirecipeTheme

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun WikirecipeApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { TopAppBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = modifier.padding(innerPadding)
        ) {
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
            composable(Screen.SavedRecipe.route) {
                SavedRecipeScreen(navigateToDetail = { id ->
                    navController.navigate(
                        Screen.Detail.createRoute(id)
                    )
                })
            }
            composable(Screen.About.route) {
                AboutScreen()
            }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(navController: NavHostController, modifier: Modifier = Modifier) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    if (currentRoute == Screen.About.route || currentRoute == Screen.SavedRecipe.route) {
        CenterAlignedTopAppBar(
            title = {
                if (currentRoute == Screen.About.route) {
                    Text(
                        "About",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                } else {
                    Text(
                        "Saved Recipes",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            },
            navigationIcon = {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            },
            scrollBehavior = scrollBehavior,
            modifier = modifier.background(color = MaterialTheme.colorScheme.primary)
        )
    } else if (currentRoute == Screen.Home.route) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    "Wikirecipe",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            actions = {
                IconButton(onClick = { navController.navigate(Screen.SavedRecipe.route) }) {
                    Icon(
                        imageVector = Icons.Filled.List,
                        contentDescription = "Localized description"
                    )
                }
                IconButton(onClick = { navController.navigate(Screen.About.route) }) {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = "Localized description"
                    )
                }
            },
            scrollBehavior = scrollBehavior,
            modifier = modifier.background(color = MaterialTheme.colorScheme.primary)
        )
    }
}