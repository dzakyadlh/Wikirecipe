package com.dzakyadlh.wikirecipe.ui.screen.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dzakyadlh.wikirecipe.R

@Composable
fun AboutScreen(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = modifier.fillMaxSize().padding(vertical = 40.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.avatar),
            contentDescription = "creator",
            modifier = modifier
                .padding(4.dp)
                .clip(
                    CircleShape
                )
                .size(200.dp)
        )
        Text(text = "Dzaky Adla", Modifier.padding(vertical = 4.dp), style = MaterialTheme.typography.bodyLarge)
        Text(text = "dzakyadlh@gmail.com", Modifier.padding(bottom = 4.dp), style = MaterialTheme.typography.bodyMedium)
        Text(text = "Software Engineer", style = MaterialTheme.typography.bodyMedium)
    }
}