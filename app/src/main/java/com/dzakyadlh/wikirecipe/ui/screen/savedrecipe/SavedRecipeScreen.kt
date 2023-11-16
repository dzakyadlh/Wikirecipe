package com.dzakyadlh.wikirecipe.ui.screen.savedrecipe

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dzakyadlh.wikirecipe.R
import com.dzakyadlh.wikirecipe.data.entity.SavedRecipe
import com.dzakyadlh.wikirecipe.ui.SavedViewModelFactory
import com.dzakyadlh.wikirecipe.ui.screen.detail.SavedRecipeViewModel
import kotlinx.coroutines.launch

@Composable
fun SavedRecipeScreen(
    modifier: Modifier = Modifier,
    viewModel: SavedRecipeViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = SavedViewModelFactory.getInstance(LocalContext.current)
    ),
    navigateToDetail: (String) -> Unit,
) {
    val savedRecipe = viewModel.getAllSavedRecipes().collectAsState(initial = emptyList()).value
    SavedRecipeContent(
        savedRecipe = savedRecipe,
        viewModel = viewModel,
        navigateToDetail = navigateToDetail,
    )
}

@Composable
fun SavedRecipeContent(
    savedRecipe: List<SavedRecipe>,
    modifier: Modifier = Modifier,
    viewModel: SavedRecipeViewModel,
    navigateToDetail: (String) -> Unit,
) {
    Box(modifier = modifier) {
        if (savedRecipe.isEmpty()) {
            Text(
                text = "You haven't saved any recipe",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                textAlign = TextAlign.Center
            )
        }
        else {
            val scope = rememberCoroutineScope()
            val listState = rememberLazyListState()
            val showButton: Boolean by remember {
                derivedStateOf { listState.firstVisibleItemIndex > 0 }
            }
            LazyColumn(state = listState, contentPadding = PaddingValues(bottom = 80.dp)) {
                items(savedRecipe, key = { it.id }) { data ->
                    SavedRecipeList(
                        id = data.id,
                        name = data.name,
                        photoUrl = data.photoUrl,
                        navigateToDetail = navigateToDetail
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
                com.dzakyadlh.wikirecipe.ui.screen.home.ScrollToTopButton(onClick = {
                    scope.launch {
                        listState.scrollToItem(
                            index = 0
                        )
                    }
                })
            }
        }
    }
}

@Composable
fun SavedRecipeList(
    id: String,
    name: String,
    photoUrl: String,
    navigateToDetail: (String) -> Unit
) {
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