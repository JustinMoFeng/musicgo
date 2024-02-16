package com.example.musiceducation.ui.composables.me

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.musiceducation.R
import com.example.musiceducation.data.NetworkUserRepository
import com.example.musiceducation.data.UserRepository
import com.example.musiceducation.network.AuthenticateApiService
import com.example.musiceducation.network.UserApiService
import com.example.musiceducation.ui.composables.common.MusicEducationBottomBar
import com.example.musiceducation.ui.theme.MusicEducationTheme
import com.example.musiceducation.ui.viewModels.UserViewModel
import okhttp3.OkHttpClient

@Composable
fun MePage(
    modifier: Modifier = Modifier,
    navController: NavController,
    userViewModel: UserViewModel = UserViewModel(NetworkUserRepository(AuthenticateApiService("", OkHttpClient()), UserApiService("", OkHttpClient())))
) {
    MusicEducationTheme {
        Scaffold(
            bottomBar = {
                MusicEducationBottomBar(selectedIndex = 2, onSelected = {
                    if(it == 2) return@MusicEducationBottomBar
                    when(it){
                        0 -> navController.navigate("book")
                        1 -> navController.navigate("forum")
                    }
                })
            }
        ) {
            Text(text = "MePage", modifier = modifier.padding(it))
        }
    }

}

@Composable
fun MePageUserPart(
    modifier: Modifier = Modifier,
    userViewModel: UserViewModel = UserViewModel(NetworkUserRepository(AuthenticateApiService("", OkHttpClient()), UserApiService("", OkHttpClient())))
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
            val modelBuilder = ImageRequest.Builder(LocalContext.current)
                .data(userViewModel.user_avatar ?: "")
                .crossfade(false)
                .allowHardware(true)
                .build()

            Spacer(modifier = Modifier.width(15.dp))

            Image(
                painter = rememberImagePainter(modelBuilder),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .clip(shape = CircleShape))

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
            )

            Spacer(modifier = Modifier.width(15.dp))

        }
    }
}

@Composable
fun MePageBodyPart(
    modifier: Modifier = Modifier,
    userViewModel: UserViewModel = UserViewModel(NetworkUserRepository(AuthenticateApiService("", OkHttpClient()), UserApiService("", OkHttpClient())))
) {
    MusicEducationTheme {
         Column(
             modifier = modifier
                 .fillMaxWidth()
                 .padding(8.dp)
                 .background(Color.White),
             horizontalAlignment = Alignment.Start
         ) {
            Row(
                modifier = Modifier.fillMaxWidth(0.9f),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "我的帖子", style = MaterialTheme.typography.titleMedium)

                Image(painter = painterResource(id = R.drawable.right_arrow), contentDescription = null)
            }
         }
    }
}




@Preview
@Composable
fun MePageUserPartPreview() {
    MePageUserPart()
}



