package com.dzakyadlh.wikirecipe.ui.screen.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import coil.compose.AsyncImage
import com.dzakyadlh.wikirecipe.R
import com.dzakyadlh.wikirecipe.data.UiState
import com.dzakyadlh.wikirecipe.data.entity.SavedRecipe
import com.dzakyadlh.wikirecipe.di.Injection
import com.dzakyadlh.wikirecipe.ui.SavedViewModelFactory
import com.dzakyadlh.wikirecipe.ui.ViewModelFactory

@Composable
fun DetailScreen(
    id: String,
    viewModel: DetailViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    savedViewModel: SavedRecipeViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = SavedViewModelFactory.getInstance(LocalContext.current)
    ),
    navigateBack: () -> Unit
) {
    val isSaved = remember {
        mutableStateOf(false)
    }
    var savedRecipe: SavedRecipe? = null
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getRecipeById(id)
            }

            is UiState.Success -> {
                val data = uiState.data
                savedRecipe =
                    SavedRecipe(data[0].id, data[0].name, data[0].description, data[0].photoUrl)
                savedViewModel.getSavedRecipe(data[0].id)
                    .observe(LocalContext.current as LifecycleOwner) { savedRecipe ->
                        isSaved.value = savedRecipe != null
                    }
                DetailContent(
                    photoUrl = data[0].photoUrl,
                    name = data[0].name,
                    description = data[0].description,
                    onBackClick = navigateBack,
                    isSaved = isSaved,
                    onSaveClick = {
                        if (isSaved.value) {
                            savedViewModel.delete(savedRecipe as SavedRecipe)
                        } else {
                            savedViewModel.insert(savedRecipe as SavedRecipe)
                        }
                    }
                )
            }

            is UiState.Error -> {}
        }
    }
}

@Composable
fun DetailContent(
    photoUrl: String,
    name: String,
    description: String,
    onBackClick: () -> Unit,
    isSaved: MutableState<Boolean>,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
        ) {
            Box {
                AsyncImage(
                    model = photoUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(300.dp)
                        .fillMaxWidth()
                )
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable { onBackClick() },
                    tint = MaterialTheme.colorScheme.primaryContainer
                )
            }
            Column(horizontalAlignment = Alignment.Start, modifier = Modifier.padding(16.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = name,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    if (isSaved.value) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_bookmark_24),
                            contentDescription = null,
                            modifier = Modifier.size(28.dp).clickable { onSaveClick() }
                        )

                    } else {
                        Icon(
                            painter = painterResource(R.drawable.baseline_bookmark_border_24),
                            contentDescription = null,
                            modifier = Modifier.size(28.dp).clickable { onSaveClick() }
                        )
                    }
                }
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify
                )
            }
        }
    }
}

//@Preview(showBackground = true, device = Devices.PIXEL_3A)
//@Composable
//fun DetailContentPreview() {
//    WikirecipeTheme {
//        DetailContent(
//            "https://firebasestorage.googleapis.com/v0/b/wikirecipe-abc14.appspot.com/o/kentucky_fried_chicken.jpg?alt=media&token=1214ad4f-79d1-4648-ad55-d0d4e197b0ac",
//            "Kentucky Fried Chicken",
//            "This is a kentucky fried chicken",
//            onBackClick = {},
//            onSaveClick = {},
//            isSaved = isSaved
//        )
//    }
//}