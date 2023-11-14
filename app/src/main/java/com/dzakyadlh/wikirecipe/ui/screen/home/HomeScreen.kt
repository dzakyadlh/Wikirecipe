package com.dzakyadlh.wikirecipe.ui.screen.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dzakyadlh.wikirecipe.R
import com.dzakyadlh.wikirecipe.data.UiState
import com.dzakyadlh.wikirecipe.di.Injection
import com.dzakyadlh.wikirecipe.model.Recipe
import com.dzakyadlh.wikirecipe.ui.ViewModelFactory
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (String) -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getRecipes()
            }

            is UiState.Success -> {
                HomeContent(
                    recipe = uiState.data,
                    modifier = modifier,
                    viewModel = viewModel,
                    navigateToDetail = navigateToDetail
                )
            }

            is UiState.Error -> {}
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeContent(
    recipe: List<Recipe>,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
    navigateToDetail: (String) -> Unit
) {
    Box(modifier = modifier) {
        val scope = rememberCoroutineScope()
        val listState = rememberLazyListState()
        val showButton: Boolean by remember {
            derivedStateOf { listState.firstVisibleItemIndex > 0 }
        }
        val query by viewModel.query
        LazyColumn(state = listState, contentPadding = PaddingValues(bottom = 80.dp)) {
            item {
                SearchBar(
                    query = query,
                    onQueryChange = viewModel::searchRecipes,
                    modifier = Modifier.background(MaterialTheme.colorScheme.primary)
                )
            }
            items(recipe, key = { it.id }) { data ->
                RecipeList(
                    id = data.id,
                    name = data.name,
                    photoUrl = data.photoUrl,
                    navigateToDetail
                )
            }
        }
        AnimatedVisibility(
            visible = showButton,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically(),
            modifier = Modifier
                .padding(bottom = 30.dp)
                .align(Alignment.BottomCenter)
        ) {
            ScrollToTopButton(onClick = { scope.launch { listState.scrollToItem(index = 0) } })
        }
    }
}

@Composable
fun RecipeList(id: String, name: String, photoUrl: String, navigateToDetail: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 8.dp)
    ) {
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .clickable { navigateToDetail(id) }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = name,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .padding(start = 16.dp),
                    textAlign = TextAlign.Center
                )
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(
                            RoundedCornerShape(
                                topStart = 0.dp,
                                topEnd = 8.dp,
                                bottomEnd = 8.dp,
                                bottomStart = 0.dp
                            )
                        )
                        .background(MaterialTheme.colorScheme.primary)
                ) {
                    AsyncImage(
                        model = photoUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
fun ScrollToTopButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FilledIconButton(onClick = onClick, modifier = modifier) {
        Icon(
            imageVector = Icons.Filled.KeyboardArrowUp,
            contentDescription = stringResource(id = R.string.scroll_to_top)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(query: String, onQueryChange: (String) -> Unit, modifier: Modifier = Modifier) {

    TextField(
        value = query,
        onValueChange = onQueryChange,
        label = { Text("Search") },
        leadingIcon = { Icon(imageVector = Icons.Filled.Search, contentDescription = null) },
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .heightIn(min = 48.dp),
    )
}