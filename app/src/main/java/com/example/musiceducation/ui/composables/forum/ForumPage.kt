package com.example.musiceducation.ui.composables.forum

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.musiceducation.config.RouteConfig
import com.example.musiceducation.ui.composables.common.MusicEducationBottomBar
import com.example.musiceducation.ui.theme.MusicEducationTheme
import com.example.musiceducation.utils.SharedPreferencesManager
import okhttp3.Route

@Composable
fun ForumPage(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    MusicEducationTheme {
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
            }
        ){
            Text(text = "ForumPage", modifier = modifier.padding(it))
        }
    }

}

@Composable
fun ForumItem(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    MusicEducationTheme {
        Text(text = "ForumItem", modifier = modifier)
    }
}
