package com.example.musiceducation.ui.composables.relationBook

import android.icu.text.CaseMap.Title
import android.media.MediaPlayer
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.musiceducation.R
import com.example.musiceducation.ui.composables.common.MusicEducationBasicTopBar
import com.example.musiceducation.ui.composables.common.MusicEducationOnlyBackTopBar
import com.example.musiceducation.ui.theme.MusicEducationTheme

val stageContentList = listOf<String>(
    "",
    "蔡漪：（向萍）他上哪去了？",
    "周萍：（莫名其妙）谁？",
    "蔡漪：你父亲",
    "周萍：他有事情，见客，一会儿就回来了，弟弟呢？",
    "蔡漪：他只会哭，他走了",
    "周萍：（怕和她一同在这间屋里）哦。（停）我要走了，我现在要收拾东西去（走向饭厅）",
    "蔡漪：回来，（萍停步）我请你略微坐一坐",
)

@Composable
fun BookStagePage(
    modifier: Modifier = Modifier,
    navController: NavController,
    title: String
) {
    MusicEducationTheme {
        Scaffold(
            topBar = {
                MusicEducationOnlyBackTopBar(title = title) {
                    navController.popBackStack()
                }
            }
        ){
            BookStagePageContent(modifier = modifier
                .padding(it)
                .fillMaxSize()
                .background(Color.White))
        }
    }
}

@Composable
fun BookStagePageContent(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var index by remember{ mutableStateOf(1) }
    var nowName by remember { mutableStateOf(getAudioResource(index)) }
    var isPlaying by remember { mutableStateOf(false) }
    val mediaPlayer = remember(nowName) {
        MediaPlayer.create(context, nowName).apply {
            isLooping = false // 使音乐不循环播放
        }.also {
            it.setOnCompletionListener {
                isPlaying = false
            }
        }
    }


    LaunchedEffect(index) {
        mediaPlayer.start()
        isPlaying = true
    }

    DisposableEffect(nowName) {
        onDispose {
            mediaPlayer.stop()
            mediaPlayer.release() // 释放资源
        }
    }
    MusicEducationTheme{
        BookStageContent(modifier = modifier, index = index, onIndexChange = {
            index = it
            nowName = getAudioResource(it)
        },isPlaying = isPlaying) {
            if (!mediaPlayer.isPlaying) {
                mediaPlayer.start()
                isPlaying = true
            }else {
                mediaPlayer.pause()
                mediaPlayer.seekTo(0)
                mediaPlayer.start()
                isPlaying = false
            }
        }
    }

}



fun getAudioResource(index: Int): Int {
    return when(index) {
        1 -> R.raw.my_audio_1
        2 -> R.raw.my_audio_2
        3 -> R.raw.my_audio_3
        4 -> R.raw.my_audio_4
        5 -> R.raw.my_audio_5
        6 -> R.raw.my_audio_6
        7 -> R.raw.my_audio_7
        else -> R.raw.my_audio_1
    }
}

@Composable
fun BookStageContent(
    modifier: Modifier = Modifier,
    index: Int,
    onIndexChange: (Int) -> Unit,
    isPlaying: Boolean,
    onClick: () -> Unit
) {
    MusicEducationTheme {
        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = stageContentList[index],
                color= Color.Black,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(0.8f)
            )

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                Button(
                    onClick = {
                        if(index > 1){
                            onIndexChange(index - 1)
                        }
                    },
                    modifier = Modifier
                        .width(120.dp)
                        .padding(5.dp)
                ) {
                    Text("上一句", color = Color.White, style = MaterialTheme.typography.titleMedium)
                }

                Spacer(modifier = Modifier.weight(0.2f))

                Button(
                    onClick = {
                        onClick()
                    },
                    modifier = Modifier
                        .width(150.dp)
                        .padding(5.dp)
                ) {
                    Text("重新播放", color = Color.White, style = MaterialTheme.typography.titleMedium)
                }

                Spacer(modifier = Modifier.weight(0.2f))

                Button(
                    onClick = {
                        if(index < 7){
                            onIndexChange(index + 1)
                        }
                    },
                    modifier = Modifier
                        .width(120.dp)
                        .padding(5.dp)
                ) {
                    Text("下一句", color = Color.White, style = MaterialTheme.typography.titleMedium)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))


        }
    }
}

@Preview
@Composable
fun BookStagePageContentPreview() {
    BookStagePageContent(modifier = Modifier)
}

@Preview
@Composable
fun BookStagePagePreview() {
    MusicEducationTheme {
        BookStagePage(modifier = Modifier, navController = rememberNavController(), title = "哈姆雷特")
    }
}



