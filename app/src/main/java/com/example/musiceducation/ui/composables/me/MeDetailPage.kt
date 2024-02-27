package com.example.musiceducation.ui.composables.me

import android.app.AlertDialog
import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.musiceducation.R
import com.example.musiceducation.config.RouteConfig
import com.example.musiceducation.ui.composables.common.MusicEducationOnlyBackTopBar
import com.example.musiceducation.ui.theme.MusicEducationTheme
import com.example.musiceducation.ui.viewModels.UserViewModel
import kotlinx.serialization.builtins.MapEntrySerializer

@Composable
fun MeDetailPage(
    viewModel: UserViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    MusicEducationTheme {
        Scaffold(
            topBar = {
                MusicEducationOnlyBackTopBar(title = "个人信息") {
                    navController.popBackStack()
                }
            }
        ) {
            MeDetailPageContent(
                userViewModel = viewModel,
                navController = navController,
                modifier = modifier
                    .padding(it)
                    .fillMaxSize()
                    .background(Color.White)
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MeDetailPageContent(
    userViewModel: UserViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    MusicEducationTheme {

        var isRevisingNickname by remember { mutableStateOf(false) }
        val keyboardController = LocalSoftwareKeyboardController.current

        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {

            // 头像

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Spacer(modifier = Modifier.width(10.dp))
                    if(userViewModel.user_avatar!=""){
                        val modelBuilder = ImageRequest.Builder(LocalContext.current)
                            .data(userViewModel.user_avatar ?: "")
                            .crossfade(false)
                            .allowHardware(true)
                            .build()

                        Image(
                            painter = rememberImagePainter(modelBuilder),
                            contentDescription = null,
                            modifier = Modifier
                                .size(50.dp)
                                .clip(shape = RoundedCornerShape(5.dp)))
                    }else {

                        Image(
                            painter = painterResource(id = R.drawable.me_user_avatar),
                            contentDescription = null,
                            modifier = Modifier
                                .size(50.dp)
                                .clip(shape = RoundedCornerShape(5.dp))
                        )
                    }


                Spacer(modifier = Modifier.weight(1f))

                Image(
                    painter = painterResource(id = R.drawable.right_arrow),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )

                Spacer(modifier = Modifier.width(10.dp))
            }

            // 用户名
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = "用户名：",color = Color.Black)
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "${userViewModel.user_name}",
                    color = Color.Black,
                    modifier = Modifier.wrapContentWidth()
                )
                Spacer(modifier = Modifier.width(10.dp))
            }

            // 昵称
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = "昵称：",color = Color.Black)
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "${userViewModel.user_nickname}",
                    color = Color.Black,
                    modifier = Modifier.wrapContentWidth()
                )
                Spacer(modifier = Modifier.width(10.dp))
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(onClick = {
                userViewModel.logout()
                navController.navigate(RouteConfig.ROUTE_BOOK)

            },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp)
                    .background(
                        color = MaterialTheme.colorScheme.onSecondary,
                        shape = MaterialTheme.shapes.medium
                    ),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onSecondary)
            ) {
                Text(text = "退出登录", color = Color.White, style = MaterialTheme.typography.bodyMedium)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {
                    isRevisingNickname = true
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp)
                    .background(
                        MaterialTheme.colorScheme.primary,
                        shape = MaterialTheme.shapes.medium
                    )
            ) {
                Text(text = "修改昵称", color = Color.White, style = MaterialTheme.typography.bodyMedium)
            }



        }

        if(isRevisingNickname){
            // 一个带有输入框的底部弹窗
            Dialog(onDismissRequest = { isRevisingNickname = false}) {
                Column(
                    modifier = Modifier
                        .width(400.dp)
                        .height(200.dp)
                        .padding(10.dp)
                        .background(Color.White, shape = MaterialTheme.shapes.medium),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {

                    Spacer(modifier = Modifier.height(20.dp))

                    // 提示
                    Text(
                        text = "请输入新的昵称",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black,
                        modifier = Modifier.fillMaxWidth(0.9f)
                    )


                    Spacer(modifier = Modifier.height(10.dp))

                    // Input field
                    BasicTextField(
                        value = userViewModel.newNickname,
                        onValueChange = {
                            userViewModel.newNickname = it
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Send
                        ),
                        keyboardActions = KeyboardActions(
                            onSend = {
                                userViewModel.updateNickname()
                                keyboardController?.hide()
                            }
                        ),
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .background(Color.LightGray)
                            .height(50.dp)
                            .padding(8.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Send button
                    Button(
                        onClick = {
                            userViewModel.updateNickname()
                            keyboardController?.hide()
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .height(60.dp)
                            .padding(8.dp)
                            .background(
                                MaterialTheme.colorScheme.primary,
                                shape = MaterialTheme.shapes.medium
                            ),
                    ) {
                        Text(text = "发送", style = MaterialTheme.typography.bodyMedium, color = Color.White)
                    }
                }


            }


            if(userViewModel.updateInfoState != ""){
                AlertDialog(
                    onDismissRequest = {userViewModel.updateInfoState = "" },
                    title = {
                        if(userViewModel.updateInfoState == "true"){
                            Text(
                                text = "修改成功通知",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.Black
                            )
                        }else {
                            Text(
                                text = "修改失败通知",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.Black
                            )
                        }

                    },
                    text = {
                        if(userViewModel.updateInfoState == "true") {
                            Text(
                                text = "修改成功，取消弹窗",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Black
                            )
                        }else{
                            Text(
                                text = userViewModel.updateInfoState,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Black
                            )
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                userViewModel.updateInfoState = ""
                                isRevisingNickname = false
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
                            onClick = { userViewModel.updateInfoState = "" }
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