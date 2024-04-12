package com.example.musiceducation.ui.composables.me

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.musiceducation.R
import com.example.musiceducation.config.RouteConfig
import com.example.musiceducation.data.NetworkUserRepository
import com.example.musiceducation.data.UserRepository
import com.example.musiceducation.network.AuthenticateApiService
import com.example.musiceducation.network.UserApiService
import com.example.musiceducation.ui.composables.common.MusicEducationBasicTopBar
import com.example.musiceducation.ui.composables.common.MusicEducationBottomBar
import com.example.musiceducation.ui.theme.MusicEducationTheme
import com.example.musiceducation.ui.viewModels.UserViewModel
import okhttp3.OkHttpClient
import okhttp3.Route
import java.time.format.TextStyle

@Composable
fun MePage(
    modifier: Modifier = Modifier,
    navController: NavController,
    userViewModel: UserViewModel = UserViewModel(NetworkUserRepository(AuthenticateApiService("", OkHttpClient()), UserApiService("", OkHttpClient())))
) {
    MusicEducationTheme {

        DisposableEffect(Unit) {
            // Call getUserInfo every time MePage is started
            userViewModel.getUserInfo()

            // onDispose block is optional and can be used for cleanup if needed
            onDispose {
                // Cleanup code, if any
            }
        }

        if(userViewModel.user_request_info != ""){
            AlertDialog(
                onDismissRequest = { userViewModel.user_request_info = "" },
                title = {
                        Text(
                            text = "请求失败通知",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.Black
                        )
                },
                containerColor = Color.LightGray,
                text = {
                        Text(
                            text = userViewModel.user_request_info,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Black
                        )
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if(userViewModel.user_request_info == "尚未登录,禁止请求"||userViewModel.user_request_info == "登录失效,请重新登录"){
                                navController.navigate(RouteConfig.ROUTE_LOGIN)
                            }
                            userViewModel.user_request_info = ""
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
                        onClick = { userViewModel.user_request_info = "" }
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

        Scaffold(
            bottomBar = {
                MusicEducationBottomBar(selectedIndex = 3, onSelected = {
                    if(it == 3) return@MusicEducationBottomBar
                    when(it){
                        0 -> navController.navigate(RouteConfig.ROUTE_BOOK)
                        1 -> navController.navigate(RouteConfig.ROUTE_RELATON_BOOK)
                        2 -> navController.navigate(RouteConfig.ROUTE_FORUM)
                    }
                })
            },
            topBar = {
                MusicEducationBasicTopBar(title = "我的")
            }
        ) {

            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(Color.White)
                    .padding(it)
            ) {
                MePageUserPart(userViewModel = userViewModel, navController = navController)
                MePageBodyPart(userViewModel = userViewModel)
            }
        }
    }

}

@Composable
fun MePageUserPart(
    modifier: Modifier = Modifier,
    userViewModel: UserViewModel = UserViewModel(NetworkUserRepository(AuthenticateApiService("", OkHttpClient()), UserApiService("", OkHttpClient()))),
    navController: NavController
) {
    MusicEducationTheme {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(Color.White),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {


            Spacer(modifier = Modifier.width(15.dp))

            if(userViewModel.user_avatar!=""&&userViewModel.user_avatar!=null){
                val modelBuilder = ImageRequest.Builder(LocalContext.current)
                    .data(userViewModel.user_avatar ?: "")
                    .crossfade(false)
                    .allowHardware(true)
                    .build()

                Image(
                    painter = rememberImagePainter(modelBuilder),
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(shape = CircleShape))
            }else {
                Image(
                    painter = painterResource(id = R.drawable.me_user_avatar),
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(shape = CircleShape)
                )
            }



            Spacer(modifier = Modifier.width(5.dp))

            Text(
                text = userViewModel.user_nickname,
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f),
                color = Color.Black,
                style = MaterialTheme.typography.titleMedium

            )

            Spacer(modifier = Modifier.width(5.dp))

            Image(
                painter = painterResource(id = R.drawable.me_setting),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .clip(shape = CircleShape)
                    .clickable {
                        navController.navigate(RouteConfig.ROUTE_USER_INFO)
                    }
            )

            Spacer(modifier = Modifier.width(15.dp))

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MePageBodyPart(
    modifier: Modifier = Modifier,
    userViewModel: UserViewModel = UserViewModel(NetworkUserRepository(AuthenticateApiService("", OkHttpClient()), UserApiService("", OkHttpClient())))
) {

    var logOutConfirm by remember { mutableStateOf(false) }

    MusicEducationTheme {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(50.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = "扩展工具",
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

            Column(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(50.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = "相关链接",
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


            Spacer(modifier = Modifier.height(20.dp))

            Box(modifier = Modifier
                .height(40.dp)
                .fillMaxWidth(0.8f)
                .clickable { logOutConfirm = true }
                .background(
                    MaterialTheme.colorScheme.onSecondary,
                    shape = MaterialTheme.shapes.medium
                ),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = "退出登录",
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium,
                )
            }

            if(logOutConfirm){
                // 一个弹窗，确认是不是要退出登陆，确认和取消两个按钮
                // 确认退出登陆后，调用userViewModel.logout()
                AlertDialog(
                    onDismissRequest = { logOutConfirm = false },
                    title = {
                        Text(
                            text = "确认退出登录",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )
                    },
                    text = {
                        Text(
                            text = "退出登录后，您的账号信息将被清除，下次登录需要重新输入账号密码",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White
                        )
                    },
                    containerColor = Color.LightGray,
                    confirmButton = {
                        Button(
                            onClick = {
                                logOutConfirm = false
                                userViewModel.logout()
                            },
                            modifier = Modifier.background(MaterialTheme.colorScheme.primary)
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
                            onClick = { logOutConfirm = false },
                            modifier = Modifier.background(MaterialTheme.colorScheme.primary)
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





        }
    }
}



@Preview
@Composable
fun MePageUserPartPreview() {
    MePageUserPart(navController = rememberNavController())
}

@Preview
@Composable
fun MePageBodyPartPreview() {
    MePageBodyPart()
}

@Preview
@Composable
fun MePagePreview() {
    MePage(navController = NavController(LocalContext.current))
}


