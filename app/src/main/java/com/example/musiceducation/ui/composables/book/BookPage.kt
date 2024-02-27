package com.example.musiceducation.ui.composables.book

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeCompilerApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.musiceducation.R
import com.example.musiceducation.config.RouteConfig
import com.example.musiceducation.ui.composables.common.MusicEducationBasicTopBar
import com.example.musiceducation.ui.composables.common.MusicEducationBottomBar
import com.example.musiceducation.ui.theme.MusicEducationTheme
import com.example.musiceducation.utils.SharedPreferencesManager

@Composable
fun BookPage(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    MusicEducationTheme {
        Scaffold(
            bottomBar = {
                MusicEducationBottomBar(selectedIndex = 0, onSelected = {
                    if(it == 0) return@MusicEducationBottomBar
                    when(it){
                        1 -> navController.navigate(RouteConfig.ROUTE_FORUM)
                        2 -> {
                            navController.navigate(RouteConfig.ROUTE_ME)
                        }
                    }
                })
            },
            topBar = {
                MusicEducationBasicTopBar(title = "书籍")
            }
        ){
            BookPageContent(modifier = modifier
                .fillMaxSize()
                .padding(it)
                .background(Color.White),
                onclick = { name, page ->
                    navController.navigate(RouteConfig.ROUTE_BOOK_READ+"/$name/$page")
                }
            )
        }
    }

}

@Composable
fun BookPageContent(
    modifier: Modifier = Modifier,
    onclick: (name: String, page: Int) -> Unit
) {
    MusicEducationTheme {
        Column(
            modifier = modifier.verticalScroll(rememberScrollState())
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ){
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onclick("哈姆雷特", 0) },
                    horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally

                ) {
                    Image(
                        painter = painterResource(id = R.drawable.book_hamlet),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .aspectRatio(0.8f)

                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "哈姆雷特",
                        modifier = Modifier.fillMaxWidth(0.8f),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onclick("音乐理论基础", 0) },
                    horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally

                ) {
                    Image(
                        painter = painterResource(id = R.drawable.book_music_basic),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .aspectRatio(0.8f)

                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "音乐理论基础",
                        modifier = Modifier.fillMaxWidth(0.8f),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ){
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onclick("选择必修5 音乐基础理论", 0) },
                    horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally

                ) {
                    Image(
                        painter = painterResource(id = R.drawable.book_music_education),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .aspectRatio(0.8f)

                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "选择必修5",
                        modifier = Modifier.fillMaxWidth(0.8f),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onclick("基本乐理教程", 0) },
                    horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally

                ) {
                    Image(
                        painter = painterResource(id = R.drawable.book_basic_music_education),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .aspectRatio(0.8f)

                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "音乐理论基础",
                        modifier = Modifier.fillMaxWidth(0.8f),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ){
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onclick("基本乐理通用教材", 0) },
                    horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally

                ) {
                    Image(
                        painter = painterResource(id = R.drawable.book_basic_music_common_education),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .aspectRatio(0.8f)

                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "选择必修5",
                        modifier = Modifier.fillMaxWidth(0.8f),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(1f),
//                        .clickable { onclick("基本乐理教程", 0) },
                    horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally

                ) {
//                    Image(
//                        painter = painterResource(id = R.drawable.book_basic_music_education),
//                        contentDescription = null,
//                        modifier = Modifier
//                            .fillMaxWidth(0.8f)
//                            .aspectRatio(0.8f)
//
//                    )
//
//                    Spacer(modifier = Modifier.height(8.dp))
//
//                    Text(
//                        text = "音乐理论基础",
//                        modifier = Modifier.fillMaxWidth(0.8f),
//                        textAlign = TextAlign.Center,
//                        style = MaterialTheme.typography.titleMedium,
//                        color = Color.Black
//                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun BookPageContentPreview() {
    BookPageContent(onclick = { _, _ -> })
}

@Preview
@Composable
fun BookPagePreview() {
    BookPage(navController = rememberNavController())
}

