package com.dzakyadlh.wikirecipe

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dzakyadlh.wikirecipe.model.RecipeData
import com.dzakyadlh.wikirecipe.ui.theme.WikirecipeTheme

@Composable
fun WikirecipeApp(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        LazyColumn{
            items(RecipeData.recipes, key = {it.id}) {recipe ->
                RecipeListItem(name = recipe.name, photoUrl = recipe.photoUrl, modifier = Modifier.fillMaxWidth())
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

@Composable
fun RecipeListItem(
    name: String,
    photoUrl: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable { }
    ) {
        AsyncImage(
            model = photoUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(8.dp)
                .size(60.dp)
                .clip(
                    CircleShape
                )
        )
        Text(
            text = name,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(start = 16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeListItemPreview(){
    WikirecipeTheme {
        RecipeListItem(name = "Kentucky Fried Chicken", photoUrl = "")
    }
}