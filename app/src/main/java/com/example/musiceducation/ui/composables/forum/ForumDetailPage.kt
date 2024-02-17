package com.example.musiceducation.ui.composables.forum

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.musiceducation.R
import com.example.musiceducation.entity.DetailForumItem
import com.example.musiceducation.entity.ForumItemAdditional
import com.example.musiceducation.ui.theme.MusicEducationTheme

@Composable
fun ForumDetailPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    forumId: Int
) {

}

@Composable
fun ForumDetailPageContent(
    forumDetailItem: DetailForumItem,
    modifier: Modifier = Modifier
){
    MusicEducationTheme {
        Column(
            modifier = modifier.fillMaxWidth(0.9f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {

                if(forumDetailItem.author_avatar!=""&& forumDetailItem.author_avatar!=null){
                    val modelBuilder = ImageRequest.Builder(LocalContext.current)
                        .data(forumDetailItem.author_avatar ?: "")
                        .crossfade(false)
                        .allowHardware(true)
                        .build()

                    Image(
                        painter = rememberImagePainter(modelBuilder),
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(shape = CircleShape))
                }else {
                    Image(
                        painter = painterResource(id = R.drawable.me_user_avatar),
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(shape = CircleShape)
                    )
                }

                Text(
                    text = forumDetailItem.author_name,
                    modifier = Modifier.padding(start = 10.dp),
                    color = Color.Black,
                    style = MaterialTheme.typography.titleLarge
                )

            }

            Text(
                text = forumDetailItem.title,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth(),
                color = Color.Black,
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = forumDetailItem.content,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth(),
                color = Color.Black,
                style = MaterialTheme.typography.bodyMedium
            )

            // 点赞逻辑暂未完成

            // 附件部分


        }
    }

}


@Composable
fun ForumItemAdditionalFile(
    modifier: Modifier = Modifier,
    file:ForumItemAdditional
){

}