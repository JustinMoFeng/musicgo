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
import com.example.musiceducation.ui.composables.common.MusicEducationOnlyBackTopBar
import com.example.musiceducation.ui.theme.MusicEducationTheme
import com.example.musiceducation.ui.viewModels.UserViewModel


@Composable
fun RegisterPage(
    modifier: Modifier = Modifier,
    userViewModel: UserViewModel,
    navController: NavController
) {
    Scaffold(
        topBar = {
            MusicEducationOnlyBackTopBar(title = "注册", onBack = {
                navController.popBackStack()
            })
        }
    ) {
        RegisterPageContent(
            modifier = modifier.padding(it),
            userViewModel = userViewModel,
            navController = navController
        )
    }

}

@Composable
fun RegisterPageContent(
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



            RegisterBody(
                modifier = Modifier
                    .fillMaxWidth(),
                authenticateViewModel = userViewModel,
                onGoToLogin = {
                    navController.navigate("login")
                }
            )

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun RegisterBody(modifier: Modifier, authenticateViewModel: UserViewModel, onGoToLogin: () -> Unit) {
    MusicEducationTheme {
        var showDialog by remember { mutableStateOf(false) }
        var showFailureDialog by remember { mutableStateOf(false) }
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
                onValueChange = { authenticateViewModel.register_name = it },
                imageValue = R.drawable.authenticate_username,
                placeHolderValue = "请输入用户名",
                username = authenticateViewModel.register_name
            )


            Spacer(modifier = Modifier.height(30.dp))


            TextInput(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .wrapContentHeight(),
                onValueChange = { authenticateViewModel.register_nickname = it },
                imageValue = R.drawable.authenticate_nickname,
                placeHolderValue = "请输入昵称",
                username = authenticateViewModel.register_nickname
            )

            Spacer(modifier = Modifier.height(30.dp))


            PasswordTextInput(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .wrapContentHeight(),
                onValueChange = { authenticateViewModel.register_password = it },
                imageValue = R.drawable.authenticate_password,
                placeHolderValue = "请输入密码",
                password = authenticateViewModel.register_password
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
                    text = "前往登陆",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .clickable {
                            onGoToLogin()
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
                        if (authenticateViewModel.register_name.isEmpty() || authenticateViewModel.register_nickname.isEmpty() || authenticateViewModel.register_password.isEmpty()) {
                            authenticateViewModel.registerState = "请填写完整信息"
                            showFailureDialog = true
                            return@clickable
                        }
                        authenticateViewModel.register()
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "注册",
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )
            }

            if(authenticateViewModel.registerState=="true") {
                AlertDialog(
                    onDismissRequest = { showFailureDialog = false },
                    title = { Text("提示") },
                    text = { Text("注册成功") },
                    confirmButton = {
                        Button(
                            onClick = {
                                authenticateViewModel.registerState = ""
                                showFailureDialog = false
                                onGoToLogin()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondary,
                                contentColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Text("确定", color = MaterialTheme.colorScheme.primary)
                        }
                    },
                    containerColor = MaterialTheme.colorScheme.primary,
                    textContentColor = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.onSecondary,
                            shape = RoundedCornerShape(10.dp)
                        )
                )
            }else if(authenticateViewModel.registerState!=""){
                AlertDialog(
                    onDismissRequest = { showFailureDialog = false },
                    title = { Text("提示") },
                    text = { Text(authenticateViewModel.registerState) },
                    confirmButton = {
                        Button(
                            onClick = {
                                authenticateViewModel.registerState = ""
                                showFailureDialog = false
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondary,
                                contentColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Text("确定", color = MaterialTheme.colorScheme.primary)
                        }
                    },
                    containerColor = MaterialTheme.colorScheme.primary,
                    textContentColor = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.onSecondary,
                            shape = RoundedCornerShape(10.dp)
                        )
                )
            }

        }
    }
}


@Composable
fun TextInput(
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    imageValue:Int,
    placeHolderValue:String,
    username:String
) {
    MusicEducationTheme {
        var isFocused by remember { mutableStateOf(false) }

        Column(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(Color.White),
        ) {
            Row(
                modifier = Modifier
                    .height(45.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Spacer(modifier = Modifier.width(8.dp))

                Image(
                    painter = painterResource(id = imageValue),
                    contentDescription = "Username",
                    modifier = Modifier.size(20.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                BasicTextField(
                    value = username,
                    onValueChange = onValueChange,
                    singleLine = true,
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                    textStyle = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.primary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.small)
                        .onFocusChanged { focusState ->
                            isFocused = focusState.isFocused
                        },
                    decorationBox = { innerTextField ->
                        Box(
                            contentAlignment = Alignment.CenterStart,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp)
                        ) {
                            if (username.isEmpty()) {
                                Text(
                                    text = placeHolderValue,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                            innerTextField()
                        }
                    }
                )
            }

            Spacer(modifier = Modifier
                .height(2.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
            )
        }
    }
}

@Composable
fun PasswordTextInput(
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    imageValue:Int,
    placeHolderValue:String,
    password:String
) {

    MusicEducationTheme {
        var isFocused by remember { mutableStateOf(false) }
        var passwordVisible by remember { mutableStateOf(false) }

        Column(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(Color.White),
        ) {
            Row(
                modifier = Modifier
                    .height(45.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Spacer(modifier = Modifier.width(8.dp))

                Image(
                    painter = painterResource(id = imageValue),
                    contentDescription = "Username",
                    modifier = Modifier.size(20.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                BasicTextField(
                    value = password,
                    onValueChange = onValueChange,
                    singleLine = true,
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                    textStyle = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.primary),
                    visualTransformation = if (passwordVisible) None else PasswordVisualTransformation(),
                    modifier = Modifier
                        .weight(1f)
                        .clip(MaterialTheme.shapes.small)
                        .onFocusChanged { focusState ->
                            isFocused = focusState.isFocused
                        },
                    decorationBox = { innerTextField ->
                        Box(
                            contentAlignment = Alignment.CenterStart,
                            modifier = Modifier
                                .padding(start = 8.dp)
                        ) {
                            if (password.isEmpty()) {
                                Text(
                                    text = placeHolderValue,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                            innerTextField()
                        }
                    }
                )

                Spacer(modifier = Modifier.width(8.dp))

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Image(
                        painter = if (passwordVisible) painterResource(id = R.drawable.authenticate_password_visible) else painterResource(id = R.drawable.authenticate_password_invisible),
                        contentDescription = "Toggle Password Visibility",
                        modifier = Modifier.size(20.dp)
                    )
                }

            }

            Spacer(modifier = Modifier
                .height(2.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
            )
        }
    }
}

//@Preview
//@Composable
//fun RegisterBodyPreview(){
//    RegisterBody(modifier = Modifier, authenticateViewModel = UserViewModel(), onGoToLogin = {})
//}
//
//@Preview
//@Composable
//fun RegisterPageContentPreview(){
//    RegisterPageContent(modifier = Modifier, userViewModel = UserViewModel(), navController = rememberNavController())
//}
//
//@Preview
//@Composable
//fun RegisterPagePreview(){
//    RegisterPage(modifier = Modifier, userViewModel = UserViewModel(), navController = rememberNavController())
//}


