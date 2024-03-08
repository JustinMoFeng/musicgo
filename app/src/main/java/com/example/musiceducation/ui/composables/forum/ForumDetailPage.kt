package com.example.musiceducation.ui.composables.forum

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
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
import com.example.musiceducation.data.NetworkForumRepository
import com.example.musiceducation.entity.DetailForumItem
import com.example.musiceducation.entity.ErrorResult
import com.example.musiceducation.entity.ForumItemAdditional
import com.example.musiceducation.entity.ForumItemCritical
import com.example.musiceducation.network.ForumApiService
import com.example.musiceducation.ui.composables.book.DirectoryItemFromDirectory
import com.example.musiceducation.ui.composables.common.Directory
import com.example.musiceducation.ui.composables.common.DirectoryList
import com.example.musiceducation.ui.composables.common.MusicEducationOnlyBackTopBar
import com.example.musiceducation.ui.theme.MusicEducationTheme
import com.example.musiceducation.ui.viewModels.ForumViewModel
import com.example.musiceducation.utils.Time
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import okhttp3.OkHttpClient
import java.net.URLEncoder

@Composable
fun ForumDetailPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    forumId: Int,
    forumViewModel: ForumViewModel
) {
    MusicEducationTheme {



        DisposableEffect(Unit) {
            // Call getUserInfo every time MePage is started
            forumViewModel.getForumById(forumId)

            // onDispose block is optional and can be used for cleanup if needed
            onDispose {
                // Cleanup code, if any
            }
        }

        Scaffold(
            topBar = {
                MusicEducationOnlyBackTopBar(title = "论坛详情", onBack = {
                    navController.popBackStack()
                })
            }
        ) {

            ForumDetailPageContent(
                forumDetailItem = forumViewModel.forumDetail,
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .background(Color.White),
                navController = navController,
                forumViewModel = forumViewModel
            )

        }


    }

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ForumDetailPageContent(
    forumDetailItem: DetailForumItem,
    modifier: Modifier = Modifier,
    navController: NavController,
    forumViewModel: ForumViewModel = ForumViewModel(NetworkForumRepository(ForumApiService("", OkHttpClient())))
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    MusicEducationTheme {
        if(forumViewModel.addCatalogResult!=""){
            AlertDialog(
                onDismissRequest = { forumViewModel.criticBackInfo = "" },
                title = {
                    if(forumViewModel.addCatalogResult == "true"){
                        Text(
                            text = "添加成功通知",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.Black
                        )
                    }else {
                        Text(
                            text = "添加失败通知",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.Black
                        )
                    }

                },
                containerColor = Color.LightGray,
                text = {
                    if(forumViewModel.addCatalogResult == "true") {
                        Text(
                            text = "添加成功，可前往对应书籍界面查看",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Black
                        )
                    }else{
                        Text(
                            text = "添加失败，请再次尝试",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Black
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            forumViewModel.addCatalogResult = ""
                        }
                    ) {
                        Text(
                            text = "确认",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )
                    }
                }
            )
        }
        Column(
            modifier = modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .weight(1f)
                    .verticalScroll(
                        rememberScrollState()
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(0.9f),
                ) {

                    if (forumDetailItem.author_avatar != "" && forumDetailItem.author_avatar != null) {
                        val modelBuilder = ImageRequest
                            .Builder(LocalContext.current)
                            .data(forumDetailItem.author_avatar ?: "")
                            .crossfade(false)
                            .allowHardware(true)
                            .build()

                        Image(
                            painter = rememberImagePainter(modelBuilder),
                            contentDescription = null,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(shape = CircleShape)
                        )
                    } else {
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
                        .fillMaxWidth(0.9f),
                    color = Color.Black,
                    style = MaterialTheme.typography.titleLarge
                )

                Text(
                    text = forumDetailItem.content,
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth(0.9f),
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(15.dp))
                val json = Json {
                    serializersModule = SerializersModule {
                        polymorphic(Directory::class) {
                            subclass(Directory.InternelLink::class)
                            subclass(Directory.ExternalURILink::class)
                            subclass(Directory.ExternalBookLink::class)
                        }
                    }
                    classDiscriminator = "type"
                }
                // 书籍链接部分
                if (forumDetailItem.book_link != "") {
                    val book_link = json.decodeFromString<DirectoryList>(forumDetailItem.book_link).directories
                    Text(
                        text = "书籍链接",
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .fillMaxWidth(0.9f),
                        color = Color.Black,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(5.dp))
                    book_link.forEach {
                        DirectoryItemFromDirectory(
                            directoryItem = it,
                            navController = navController
                        )

                        Button(onClick = { forumViewModel.addToCatalog(it) }) {
                            Text(text = "将目录加入书籍",color = Color.White)
                        }
                    }
                }

                // 附件部分
                if (forumDetailItem.additionalList.isNotEmpty()) {
                    forumDetailItem.additionalList.forEach {
                        ForumItemAdditionalFile(
                            file = it,
                            navController = navController
                        )

                        Spacer(modifier = Modifier.height(5.dp))
                    }
                }

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = "发布于${Time.formatTime(forumDetailItem.time)}",
                    modifier = Modifier,
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodySmall
                )

                // 点赞逻辑暂未完成


                // 评论部分
                Text(
                    text = "评论",
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth(0.9f),
                    color = Color.Black,
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(10.dp))

                if (forumDetailItem.criticList.isNotEmpty()) {
                    forumDetailItem.criticList.forEach {
                        ForumItemCriticalOneItem(
                            criticItem = it
                        )

                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }




            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(56.dp)
                    .align(Alignment.End)
                    .background(Color.LightGray),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 输入框

                Spacer(modifier = Modifier.width(10.dp))

                BasicTextField(
                    value = forumViewModel.forumCriticInput,
                    onValueChange = {
                        forumViewModel.forumCriticInput = it
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Text
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                        }
                    ),
                    textStyle = TextStyle(
                        fontSize = 16.sp,
                        textAlign = TextAlign.Start,
                        color = Color.Black
                    ),
                    modifier = Modifier
                        .weight(5f)
                        .background(MaterialTheme.colorScheme.secondary, RoundedCornerShape(5.dp))
                        .heightIn(40.dp),
                    decorationBox = { innerTextField ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 10.dp, end = 10.dp)
                                .padding(vertical = 5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            innerTextField()
                        }
                    },
                    singleLine = false
                )

                // 发送按钮
                IconButton(
                    onClick = {
                        // 处理发送按钮点击事件
                        // 这里可以添加你的逻辑，例如发送评论等
                        // 清空输入框内容
                        forumViewModel.sendForumCritic(forumDetailItem.id)
                        forumViewModel.forumCriticInput = ""
                    },
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(5.dp))
                        .width(60.dp)
                        .height(40.dp)
                ) {
                    Text(text = "发送", color = Color.White)
                }

                Spacer(modifier = Modifier.width(10.dp))
            }


        }

        if(forumViewModel.criticBackInfo != ""){
            // 一个弹窗，确认是不是要退出登陆，确认和取消两个按钮
            // 确认退出登陆后，调用userViewModel.logout()
            AlertDialog(
                onDismissRequest = { forumViewModel.criticBackInfo = "" },
                title = {
                    if(forumViewModel.criticBackInfo == "true"){
                        Text(
                            text = "发布成功通知",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.Black
                        )
                    }else {
                        Text(
                            text = "发布失败通知",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.Black
                        )
                    }

                },
                containerColor = Color.LightGray,
                text = {
                    if(forumViewModel.criticBackInfo == "true") {
                        Text(
                            text = "发布成功，确认刷新页面",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Black
                        )
                    }else{
                        Text(
                            text = forumViewModel.criticBackInfo,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Black
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            forumViewModel.criticBackInfo = ""
                            navController.popBackStack()
                            navController.navigate(RouteConfig.ROUTE_FORUM_DETAIL+"/${forumDetailItem.id}")
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
                        onClick = { forumViewModel.criticBackInfo = "" }
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


@Composable
fun ForumItemAdditionalFile(
    modifier: Modifier = Modifier,
    navController: NavController,
    file: ForumItemAdditional
) {

    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            // 处理下载完成后的逻辑，可以根据需求进行操作
        }

    Row(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .background(Color.LightGray, RoundedCornerShape(5.dp))
            .padding(6.dp)
            .clickable {
                val uri = Uri.parse(file.file_url)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                launcher.launch(intent)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {

        if (file.file_type.startsWith("image/")) {
            Image(
                painter = painterResource(id = R.drawable.file_image),
                contentDescription = "图片文件",
                modifier = Modifier.size(40.dp)
            )
        } else if (file.file_type.startsWith("audio/")) {
            Image(
                painter = painterResource(id = R.drawable.file_voice),
                contentDescription = "音频文件",
                modifier = Modifier.size(40.dp)
            )
        } else if (file.file_type.startsWith("video/")) {
            Image(
                painter = painterResource(id = R.drawable.file_video),
                contentDescription = "视频文件",
                modifier = Modifier.size(40.dp)
            )
        } else if (file.file_type.startsWith("application/pdf")) {
            Image(
                painter = painterResource(id = R.drawable.file_pdf),
                contentDescription = "PDF文件",
                modifier = Modifier.size(40.dp)
            )
        } else if (file.file_type.startsWith("text/")) {
            Image(
                painter = painterResource(id = R.drawable.file_txt),
                contentDescription = "文本文件",
                modifier = Modifier.size(40.dp)
            )
        } else if (file.file_type.startsWith("application/vnd.ms-powerpoint")) {
            Image(
                painter = painterResource(id = R.drawable.file_powerpoint),
                contentDescription = "PPT文件",
                modifier = Modifier.size(40.dp)
            )
        } else if (file.file_type.startsWith("application/vnd.ms-excel")) {
            Image(
                painter = painterResource(id = R.drawable.file_excel),
                contentDescription = "Excel文件",
                modifier = Modifier.size(40.dp)
            )
        } else if (file.file_type.startsWith("application/msword")) {
            Image(
                painter = painterResource(id = R.drawable.file_word),
                contentDescription = "Word文件",
                modifier = Modifier.size(40.dp)
            )

        } else if (file.file_type.startsWith("application/zip")) {
            Image(
                painter = painterResource(id = R.drawable.file_zip),
                contentDescription = "压缩文件",
                modifier = Modifier.size(40.dp)
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.file_unknown),
                contentDescription = "未知文件",
                modifier = Modifier.size(40.dp)
            )
        }

        Text(
            text = file.file_name,
            modifier = Modifier.padding(horizontal = 10.dp),
            color = Color.Black,
            style = MaterialTheme.typography.bodyMedium
        )

    }

}

@Composable
fun ForumItemCriticalOneItem(
    modifier: Modifier = Modifier,
    criticItem: ForumItemCritical
) {
    Column(
        modifier = modifier.fillMaxWidth(0.9f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {

            if (criticItem.critic_author_avatar != "" && criticItem.critic_author_avatar != null) {
                val modelBuilder = ImageRequest.Builder(LocalContext.current)
                    .data(criticItem.critic_author_avatar ?: "")
                    .crossfade(false)
                    .allowHardware(true)
                    .build()

                Image(
                    painter = rememberImagePainter(modelBuilder),
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)
                        .clip(shape = CircleShape)
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.me_user_avatar),
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)
                        .clip(shape = CircleShape)
                )
            }

            Text(
                text = criticItem.critic_author_name,
                modifier = Modifier.padding(start = 10.dp),
                color = Color.Black,
                style = MaterialTheme.typography.titleSmall
            )

        }

        Text(
            text = criticItem.critic_content,
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth(0.8f),
            color = Color.Black,
            style = MaterialTheme.typography.bodyMedium
        )

        Text(
            text = "发布于${Time.formatTime(criticItem.critic_time)}",
            modifier = Modifier
                .padding(top = 5.dp, bottom = 5.dp)
                .fillMaxWidth(),
            color = Color.Gray,
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Gray)
        )


    }

}


@Preview
@Composable
fun ForumDetailPagePreview() {
    MusicEducationTheme {
        ForumDetailPageContent(
            forumDetailItem = DetailForumItem(
                id = 1,
                title = "测试标题",
                content = "测试内容",
                author_name = "测试作者",
                author_avatar = "",
                time = 1630000000000,
                reply = 0,
                like = 0,
                criticList = emptyList(),
                additionalList = emptyList(),
                book_link = ""
            ),
            navController = rememberNavController(),
        )
    }
}

@Preview
@Composable
fun ForumItemAdditionalFilePreview() {
    MusicEducationTheme {
        ForumItemAdditionalFile(
            file = ForumItemAdditional(
                id = 1,
                file_name = "测试文件.md",
                file_url = "https://www.baidu.com",
                file_type = ".md",
                forum_item_id = 4,
                is_delete = false
            ),
            navController = rememberNavController()
        )
    }
}
