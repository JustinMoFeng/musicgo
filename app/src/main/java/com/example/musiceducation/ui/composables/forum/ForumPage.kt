package com.example.musiceducation.ui.composables.forum

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.musiceducation.R
import com.example.musiceducation.config.RouteConfig
import com.example.musiceducation.entity.ForumItem
import com.example.musiceducation.ui.composables.common.MusicEducationBasicTopBar
import com.example.musiceducation.ui.composables.common.MusicEducationBottomBar
import com.example.musiceducation.ui.theme.MusicEducationTheme
import com.example.musiceducation.ui.viewModels.ForumViewModel
import com.example.musiceducation.utils.SharedPreferencesManager
import com.example.musiceducation.utils.Time
import okhttp3.Route
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun ForumPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    forumViewModel: ForumViewModel
) {
    MusicEducationTheme {

        DisposableEffect(Unit) {
            // Call getUserInfo every time MePage is started
            forumViewModel.getNewForumList()

            // onDispose block is optional and can be used for cleanup if needed
            onDispose {
                // Cleanup code, if any
            }
        }

        Scaffold (
            bottomBar = {
                MusicEducationBottomBar(selectedIndex = 1, onSelected = {
                    Log.d("ForumPage", "selectedIndex: $it")
                    if(it == 1) return@MusicEducationBottomBar
                    when(it){
                        0 -> navController.navigate("book")
                        2 -> {
                            if(SharedPreferencesManager.getToken() == ""){
                                navController.navigate(RouteConfig.ROUTE_LOGIN)
                            }else{
                                navController.navigate(RouteConfig.ROUTE_ME)
                            }
                        }
                    }
                })
            },
            topBar = {
                MusicEducationBasicTopBar(title = "论坛")
            },
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(Color.White)
        ){
            Column(
                modifier = modifier
                    .padding(it)
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(Color.White)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
            ) {
                forumViewModel.forumList.forEach {

                    ForumLineItem(
                        navController = navController,
                        forumItem = it
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                }
            }
        }
    }

}

@Composable
fun ForumLineItem(
    modifier: Modifier = Modifier,
    navController: NavController,
    forumItem: ForumItem
) {
    MusicEducationTheme {
        Column(
            modifier = modifier
                .background(Color.White)
                .fillMaxWidth(0.9f),
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(5.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {
                if(forumItem.author_avatar!=""&& forumItem.author_avatar!=null){
                    val modelBuilder = ImageRequest.Builder(LocalContext.current)
                        .data(forumItem.author_avatar ?: "")
                        .crossfade(false)
                        .allowHardware(true)
                        .build()

                    Image(
                        painter = rememberImagePainter(modelBuilder),
                        contentDescription = null,
                        modifier = Modifier
                            .size(30.dp)
                            .clip(shape = CircleShape))
                }else {
                    Image(
                        painter = painterResource(id = R.drawable.me_user_avatar),
                        contentDescription = null,
                        modifier = Modifier
                            .size(30.dp)
                            .clip(shape = CircleShape)
                    )
                }

                Text(
                    text = forumItem.author_name,
                    modifier = Modifier.padding(start = 10.dp),
                    color = Color.Black,
                )

            }

            Text(
                text = forumItem.title,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth(),
                color = Color.Black,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Start
            )

            Text(
                text = forumItem.content,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth(),
                color = Color.Black,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Start,
                maxLines = 3
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "回复: ${forumItem.reply}",
                    modifier = Modifier,
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = "点赞: ${forumItem.like}",
                    modifier = Modifier,
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = "${Time.formatTime(forumItem.time)}",
                    modifier = Modifier,
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(2.dp))

            Spacer(modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(1.dp)
                .background(Color.Gray))

        }
    }
}
