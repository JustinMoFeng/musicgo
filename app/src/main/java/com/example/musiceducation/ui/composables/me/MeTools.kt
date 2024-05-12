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

val toolMap = mapOf<String,String>(
    "调音器" to "https://www.musicca.com/zh/tuner",
    "节拍器" to "https://www.musicca.com/zh/metronome",
    "音名对照器" to "https://www.musicca.com/zh/note-finder",
    "模拟钢琴" to "https://www.musicca.com/zh/piano",
    "模拟吉他" to "https://www.musicca.com/zh/guitar",
    "模拟贝斯" to "https://www.musicca.com/zh/bass-guitar",
    "模拟鼓" to "https://www.musicca.com/zh/drums",


)

@Composable
fun MeToolPage(
    modifier: Modifier = Modifier,
    navController: NavController
){
    MusicEducationTheme {
        Scaffold(
            topBar = {
                MusicEducationOnlyBackTopBar(title = "工具", onBack = {
                    navController.popBackStack()
                })
            }
        ) {
            MeToolPageContent(
                modifier = modifier.padding(it).background(Color.White),
                navController = navController
            )
        }
    }

}

@Composable
fun MeToolPageContent(
    modifier: Modifier = Modifier,
    navController: NavController
){
    MusicEducationTheme {
        Column(
            modifier = modifier.fillMaxSize(),

            ) {
            toolMap.forEach { (name, url) ->
                MeToolItem(name = name, url=url, navController = navController)
            }
        }
    }
}

@Composable
fun MeToolItem(
    name: String,
    url: String = "",
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
