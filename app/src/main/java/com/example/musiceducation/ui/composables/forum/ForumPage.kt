package com.example.musiceducation.ui.composables.forum

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
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
import com.example.musiceducation.ui.viewModels.UserViewModel
import com.example.musiceducation.utils.SharedPreferencesManager
import com.example.musiceducation.utils.Time
import okhttp3.Route
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun ForumPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    forumViewModel: ForumViewModel,
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

        if(forumViewModel.forumBackInfo == "尚未登录,禁止请求"||forumViewModel.forumBackInfo == "登录失效,请重新登录"){
            // 一个弹窗，确认是不是要退出登陆，确认和取消两个按钮
            // 确认退出登陆后，调用userViewModel.logout()
            AlertDialog(
                onDismissRequest = { forumViewModel.forumBackInfo = "" },
                title = {
                    Text(
                        text = "提示",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black
                    )

                },
                text = {
                    Text(
                        text = forumViewModel.forumBackInfo,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black
                    )
                },
                containerColor = Color.LightGray,
                confirmButton = {
                    Button(
                        onClick = {
                            forumViewModel.forumBackInfo = ""
                            navController.navigate(RouteConfig.ROUTE_LOGIN)
                        }
                    ) {
                        Text(
                            text = "确认",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )
                    }
                },
                dismissButton = {
                    Button(
                        onClick = { forumViewModel.forumBackInfo = "" }
                    ) {
                        Text(
                            text = "取消",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )
                    }
                }
            )
        }

        Scaffold (
            bottomBar = {
                MusicEducationBottomBar(selectedIndex = 2, onSelected = {
                    Log.d("ForumPage", "selectedIndex: $it")
                    if(it == 2) return@MusicEducationBottomBar
                    when(it){
                        0 -> navController.navigate(RouteConfig.ROUTE_BOOK)
                        1 -> navController.navigate(RouteConfig.ROUTE_RELATON_BOOK)
                        3 -> {
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

            Box(modifier = Modifier
                .fillMaxSize()
                .padding(it)){

                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(20.dp)
                        .zIndex(1f)
                        .background(MaterialTheme.colorScheme.primary)
                        .clickable {
                            navController.navigate(RouteConfig.ROUTE_FORUM_ADD)

                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier.size(30.dp)
                    )
                }

                Column(
                    modifier = modifier
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

                    Spacer(modifier = Modifier.height(10.dp))

                    if(!forumViewModel.isComplete){
                        Button(
                            onClick = {
                                forumViewModel.getForumList()
                            },
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .background(MaterialTheme.colorScheme.primary, shape = MaterialTheme.shapes.medium)
                        ) {
                            Text(
                                text = "加载更多",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White
                            )
                        }
                    }else{
                        Text(
                            text = "没有更多了~",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.LightGray
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))


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
                .fillMaxWidth(0.9f)
                .clickable { navController.navigate(RouteConfig.ROUTE_FORUM_DETAIL + "/${forumItem.id}") },
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
                    text = "回复: ${forumItem.reply_num}",
                    modifier = Modifier,
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = "点赞: ${forumItem.like_num}",
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
