package com.example.musiceducation.ui.composables.me

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.musiceducation.R
import com.example.musiceducation.ui.composables.common.MusicEducationBottomBar
import com.example.musiceducation.ui.theme.MusicEducationTheme

@Composable
fun MePage(
    modifier: Modifier = Modifier,
    navController: NavController
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
    modifier: Modifier = Modifier
) {
    MusicEducationTheme {
        Row(
            modifier = modifier.fillMaxWidth().height(100.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Image(
                painter = painterResource(id = R.drawable.me_user_avatar),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .clip(shape = CircleShape))

            Text(text = "User Name", modifier = Modifier.padding(8.dp))
        }
    }
}



@Preview
@Composable
fun MePageUserPartPreview() {
    MePageUserPart()
}



