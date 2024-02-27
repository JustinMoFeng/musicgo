package com.example.musiceducation.ui.composables.authenticate

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation.Companion.None
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.musiceducation.R
import com.example.musiceducation.config.RouteConfig
import com.example.musiceducation.ui.composables.common.MusicEducationOnlyBackTopBar
import com.example.musiceducation.ui.theme.MusicEducationTheme
import com.example.musiceducation.ui.viewModels.UserViewModel


@Composable
fun LoginPage(
    modifier: Modifier = Modifier,
    userViewModel: UserViewModel,
    navController: NavController
) {
    Scaffold(
        topBar = {
            MusicEducationOnlyBackTopBar(title = "登录", onBack = {
                navController.popBackStack()
            })
        }
    ) {
        LoginPageContent(
            modifier = modifier.padding(it),
            userViewModel = userViewModel,
            navController = navController
        )
    }

}

@Composable
fun LoginPageContent(
    modifier: Modifier = Modifier,
    userViewModel: UserViewModel,
    navController: NavController
) {
    MusicEducationTheme {
        Column(
            modifier = modifier.background(Color.White)
        ) {
            Spacer(modifier = Modifier.weight(0.1f))


            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "app_logo",
                modifier = Modifier
                    .weight(3f)
                    .align(Alignment.CenterHorizontally)
            )


            Spacer(modifier = Modifier.weight(0.1f))



            LoginBody(
                modifier = Modifier
                    .fillMaxWidth(),
                authenticateViewModel = userViewModel,
                onGoToRegister = {
                    navController.navigate(RouteConfig.ROUTE_REGISTER)
                },
                onGoToBookPage = {
                    navController.navigate(RouteConfig.ROUTE_BOOK)
                }
            )

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun LoginBody(modifier: Modifier, authenticateViewModel: UserViewModel, onGoToRegister: () -> Unit, onGoToBookPage: () -> Unit){
    MusicEducationTheme {
        var showDialog by remember { mutableStateOf(false) }
        Column(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TextInput(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .wrapContentHeight(),
                onValueChange = { authenticateViewModel.login_name = it },
                imageValue = R.drawable.authenticate_username,
                placeHolderValue = "请输入用户名",
                username = authenticateViewModel.login_name
            )

            Spacer(modifier = Modifier.height(30.dp))


            PasswordTextInput(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .wrapContentHeight(),
                onValueChange = { authenticateViewModel.login_password = it },
                imageValue = R.drawable.authenticate_password,
                placeHolderValue = "请输入密码",
                password = authenticateViewModel.login_password
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(0.8f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "忘记密码",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .clickable {
                            showDialog = true
                        }
                )

                if (showDialog) {
                    AlertDialog(
                        onDismissRequest = { showDialog = false },
                        title = { Text("提示") },
                        text = { Text("找回密码功能暂未开通") },
                        confirmButton = {
                            Button(
                                onClick = { showDialog = false },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.secondary,
                                    contentColor = MaterialTheme.colorScheme.primary
                                )
                            ) {
                                Text("确定", color = MaterialTheme.colorScheme.primary)
                            }
                        },
                        containerColor = MaterialTheme.colorScheme.primary,
                        textContentColor = Color.White,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.onSecondary,
                                shape = RoundedCornerShape(10.dp)
                            )
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "前往注册",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .clickable {
                            onGoToRegister()
                        }
                )

                Spacer(modifier = Modifier.width(8.dp))
            }

            Spacer(modifier = Modifier.height(50.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(horizontal = 8.dp, vertical = 8.dp)
                    .height(48.dp)
                    .background(MaterialTheme.colorScheme.primary, MaterialTheme.shapes.medium)
                    .clickable {
                        if (authenticateViewModel.login_name.isEmpty() || authenticateViewModel.login_password.isEmpty()) {
                            authenticateViewModel.loginState = "用户名或密码不能为空"
                            return@clickable
                        }
                        authenticateViewModel.login()
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "登录",
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )
            }

            if(authenticateViewModel.loginState!=""&&authenticateViewModel.loginState!="true") {
                AlertDialog(
                    onDismissRequest = { authenticateViewModel.loginState = "" },
                    title = {
                        Text(
                            text = "登录失败通知",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.Black
                        )
                    },
                    text = {
                        Text(
                            text = authenticateViewModel.loginState,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Black
                        )
                    },
                    containerColor = Color.LightGray,
                    confirmButton = {
                        Button(
                            onClick = {
                                if(authenticateViewModel.loginState=="用户名不存在"){
                                    onGoToRegister()
                                }
                                authenticateViewModel.loginState = ""

                            },
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.primary)
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
                            onClick = { authenticateViewModel.loginState = "" },
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.primary)
                        ) {
                            Text(
                                text = "取消",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White
                            )
                        }
                    }
                )
            }else if(authenticateViewModel.loginState=="true"){
                authenticateViewModel.loginState = ""
                onGoToBookPage()
            }

        }
    }
}



//@Preview
//@Composable
//fun LoginBodyPreview(){
//    LoginBody(modifier = Modifier, authenticateViewModel = UserViewModel(), onGoToLogin = {})
//}
//
//@Preview
//@Composable
//fun LoginPageContentPreview(){
//    LoginPageContent(modifier = Modifier, userViewModel = UserViewModel(), navController = rememberNavController())
//}
//
//@Preview
//@Composable
//fun LoginPagePreview(){
//    LoginPage(modifier = Modifier, userViewModel = UserViewModel(), navController = rememberNavController())
//}


