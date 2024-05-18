package com.example.musiceducation.ui.composables.me

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.musiceducation.R
import com.example.musiceducation.config.RouteConfig
import com.example.musiceducation.ui.composables.common.MusicEducationOnlyBackTopBar
import com.example.musiceducation.ui.theme.MusicEducationTheme
import java.net.URLEncoder

val urlMap = mapOf<String,String>(
    "识谱软件 Tenuto" to "https://www.musictheory.net/",
    "视唱练耳软件 MyEarTraining" to "https://www.myeartraining.net/",
    "视唱练耳软件 EarMaster" to "https://www.earmaster.com/",

)

@Composable
fun MeLinkPage(
    modifier: Modifier = Modifier,
    navController: NavController
){
    MusicEducationTheme {
        Scaffold(
            topBar = {
                MusicEducationOnlyBackTopBar(title = "链接", onBack = {
                    navController.popBackStack()
                })
            }
        ) {
            MeLinkPageContent(
                modifier = modifier.padding(it).background(Color.White),
                navController = navController
            )
        }
    }

}

@Composable
fun MeLinkPageContent(
    modifier: Modifier = Modifier,
    navController: NavController
){
    MusicEducationTheme {
        Column(
            modifier = modifier.fillMaxSize(),

            ) {
            urlMap.forEach { (name, url) ->
                MeLinkItem(name = name, url = url, navController = navController)
            }
        }
    }
}

@Composable
fun MeLinkItem(
    name: String,
    url: String,
    navController: NavController
){
    MusicEducationTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    val encodedURL = URLEncoder.encode(url, "utf-8")
                    navController.navigate(RouteConfig.COMMON_WEBVIEW + "/$encodedURL/$name")
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(horizontal = 10.dp)
                    .height(60.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(59.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = name,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black,
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Image(
                        painter = painterResource(id = R.drawable.right_arrow),
                        contentDescription = null,
                        modifier = Modifier
                            .size(15.dp)
                            .clip(shape = CircleShape)

                    )
                    Spacer(modifier = Modifier.width(4.dp))
                }

                Spacer(modifier = Modifier
                    .height(1.dp)
                    .background(Color.Gray)
                    .fillMaxWidth()
                )

            }
        }
    }

}
