package com.dzakyadlh.wikirecipe.ui.screen.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dzakyadlh.wikirecipe.R
import com.dzakyadlh.wikirecipe.data.UiState
import com.dzakyadlh.wikirecipe.di.Injection
import com.dzakyadlh.wikirecipe.ui.ViewModelFactory
import com.dzakyadlh.wikirecipe.ui.theme.WikirecipeTheme

@Composable
fun DetailScreen(
    id: String,
    viewModel: DetailViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateBack: () -> Unit
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getRecipeById(id)
            }

            is UiState.Success -> {
                val data = uiState.data
                DetailContent(
                    photoUrl = data[0].photoUrl,
                    name = data[0].name,
                    description = data[0].description,
                    onBackClick = navigateBack
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
                Row (verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = name,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
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

@Preview(showBackground = true, device = Devices.PIXEL_3A)
@Composable
fun DetailContentPreview() {
    WikirecipeTheme {
        DetailContent(
            "https://firebasestorage.googleapis.com/v0/b/wikirecipe-abc14.appspot.com/o/kentucky_fried_chicken.jpg?alt=media&token=1214ad4f-79d1-4648-ad55-d0d4e197b0ac",
            "Kentucky Fried Chicken",
            "This is a kentucky fried chicken",
            onBackClick = {},
        )
    }
}