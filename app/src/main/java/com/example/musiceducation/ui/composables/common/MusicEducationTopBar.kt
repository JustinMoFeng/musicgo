package com.example.musiceducation.ui.composables.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.musiceducation.R

@Composable
fun MusicEducationBasicTopBar(
    modifier: Modifier = Modifier,
    title: String
){
    Row(
        modifier = modifier
            .height(56.dp)
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.primary),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = Color.White
        )
    }
}

@Composable
fun MusicEducationOnlyBackTopBar(
    modifier: Modifier = Modifier,
    title: String,
    onBack: () -> Unit,
){
    Row(
        modifier = modifier
            .height(56.dp)
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.primary),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Image(
            painter = painterResource(id = R.drawable.book_back),
            contentDescription = "back",
            modifier = Modifier
                .clickable { onBack() }
                .height(30.dp)
                .width(40.dp)
                .padding(horizontal = 8.dp)
        )

        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = Color.White
        )

        Spacer(modifier = Modifier.width(40.dp).padding(horizontal = 8.dp))
    }
}

@Composable
fun MusicEducationBookReaderTopBar(
    modifier: Modifier = Modifier,
    title: String,
    onBack: () -> Unit,
    onCatalog: () -> Unit
){
    Row(
        modifier = modifier
            .height(56.dp)
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.primary),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Image(
            painter = painterResource(id = R.drawable.book_back),
            contentDescription = "back",
            modifier = Modifier
                .clickable { onBack() }
                .height(30.dp)
                .width(40.dp)
                .padding(horizontal = 8.dp)
        )

        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = Color.White
        )

        Image(
            painter = painterResource(id = R.drawable.book_catalog),
            contentDescription = "back",
            modifier = Modifier
                .clickable { onCatalog() }
                .height(30.dp)
                .width(40.dp)
                .padding(horizontal = 8.dp)
        )
    }
}

@Preview
@Composable
fun MusicEducationBasicTopBarPreview(){
    MusicEducationBasicTopBar(title = "Title")
}

@Preview
@Composable
fun MusicEducationBookReaderTopBarPreview(){
    MusicEducationBookReaderTopBar(title = "Title", onBack = {}, onCatalog = {})
}