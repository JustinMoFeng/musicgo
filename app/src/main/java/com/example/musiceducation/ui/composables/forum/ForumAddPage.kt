package com.example.musiceducation.ui.composables.forum

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.musiceducation.R
import com.example.musiceducation.config.RouteConfig
import com.example.musiceducation.data.NetworkForumRepository
import com.example.musiceducation.data.NetworkUserRepository
import com.example.musiceducation.entity.FileInfo
import com.example.musiceducation.entity.ForumItemAdditional
import com.example.musiceducation.network.AuthenticateApiService
import com.example.musiceducation.network.ForumApiService
import com.example.musiceducation.network.UserApiService
import com.example.musiceducation.ui.composables.common.MusicEducationOnlyBackTopBar
import com.example.musiceducation.ui.theme.MusicEducationTheme
import com.example.musiceducation.ui.viewModels.ForumViewModel
import com.example.musiceducation.ui.viewModels.UserViewModel
import okhttp3.OkHttpClient

val bookName = mutableListOf(
    "请选择书籍",
    "哈姆雷特",
    "音乐理论基础",
    "选择必修5 音乐基础理论",
    "基本乐理教程",
    "基本乐理通用教材"
)

val bookLink = mutableMapOf(
    "原文" to "0",
    "内部书籍链接" to "4",
    "外部书籍链接" to "1",
    "外部链接" to "2"
)

val reverseBookLink = mutableMapOf(
    "0" to "原文",
    "4" to "内部书籍链接",
    "1" to "外部书籍链接",
    "2" to "外部链接"
)

@Composable
fun ForumAddPage(
    navController: NavController,
    modifier: Modifier = Modifier,
    forumViewModel: ForumViewModel = ForumViewModel(NetworkForumRepository(ForumApiService("", OkHttpClient())))
) {

    MusicEducationTheme {


        if(forumViewModel.newForumBackInfo.value != ""){
            // 一个弹窗，确认是不是要退出登陆，确认和取消两个按钮
            // 确认退出登陆后，调用userViewModel.logout()
            AlertDialog(
                onDismissRequest = { forumViewModel.newForumBackInfo.value = "" },
                title = {
                    if(forumViewModel.newForumBackInfo.value == "true"){
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
                    if(forumViewModel.newForumBackInfo.value == "true") {
                        Text(
                            text = "发布成功，是否返回论坛列表",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Black
                        )
                    }else{
                        Text(
                            text = forumViewModel.newForumBackInfo.value,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Black
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            forumViewModel.newForumBackInfo.value = ""
                            forumViewModel.newForumFileList.clear()
                            forumViewModel.newForumNumberContent.clear()
                            forumViewModel.newForumTitle = ""
                            forumViewModel.newBasicForumContent = ""
                            forumViewModel.newforumContentList.clear()
                            navController.popBackStack()
                            forumViewModel.getNewForumList()
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
                        onClick = {
                            forumViewModel.newForumBackInfo.value = ""
                            forumViewModel.newforumContentList.clear()
                        }
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
            topBar = {
                MusicEducationOnlyBackTopBar(title = "添加帖子") {
                    navController.popBackStack()
                    forumViewModel.newforumContentList.clear()
                }
            }
        ) {
            ForumAddPageContent(navController = navController, forumViewModel = forumViewModel, modifier = modifier
                .padding(it)
                .background(Color.White)
                .fillMaxSize()
            )
        }
    }


}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ForumAddPageContent(
    navController: NavController,
    modifier: Modifier = Modifier,
    forumViewModel: ForumViewModel
){
    MusicEducationTheme {
        val keyboardController = LocalSoftwareKeyboardController.current
        Column(
            modifier = modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
        ) {
                Text(
                    text = "帖子标题",
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(5.dp),
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black
                )
                TextField(
                    value = forumViewModel.newForumTitle,
                    onValueChange = {
                        forumViewModel.newForumTitle = it
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(5.dp),
                    label = {
                        Text(text = "请输入帖子标题")
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                        }
                    ),
                    textStyle = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(text = "帖子内容", modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(5.dp),
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black
                )

                TextField(
                    value = forumViewModel.newBasicForumContent,
                    onValueChange = {
                        forumViewModel.newBasicForumContent = it
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(5.dp),
                    label = {
                        Text(text = "请输入帖子内容")
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                        }
                    ),
                    textStyle = MaterialTheme.typography.bodyMedium,
                    maxLines = 5 // 设置最大行数
                )

            Spacer(modifier = Modifier.height(10.dp))

            ForumAddBookLinkPart(navController = navController, forumViewModel = forumViewModel)

            Spacer(modifier = Modifier.height(10.dp))

            ForumAddFilePart(navController = navController, forumViewModel = forumViewModel)

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = { forumViewModel.addForumItem() },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(5.dp)
            ) {
                Text(text = "提交",color = Color.White, style = MaterialTheme.typography.bodyMedium)
            }


        }
    }


}

private const val PICK_MULTIPLE_FILES_REQUEST_CODE = 2

@SuppressLint("Range", "MutableCollectionMutableState")
@Composable
fun ForumAddFilePart(navController: NavController, forumViewModel: ForumViewModel,modifier: Modifier = Modifier) {
    var files by remember { mutableStateOf(forumViewModel.newForumFileList) }
    var selectedFilesUri by remember { mutableStateOf<List<Uri>>(emptyList()) }
    val context = LocalContext.current
    var file_size by remember { mutableIntStateOf(0) }



    val filePickerLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetMultipleContents()) { uri: List<Uri> ->
        // 处理选择的图片
        selectedFilesUri = uri
        file_size = uri.size
        files = files.apply {
            uri.forEach { uri ->
                val cursor = context.contentResolver.query(uri, null, null, null, null)
                cursor?.use {
                    val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
                    cursor.moveToFirst()
                    val name = cursor.getString(nameIndex)
                    val size = cursor.getLong(sizeIndex)
                    val type = context.contentResolver.getType(uri)
                    add(FileInfo(name, type ?: "", uri.toString()))
                    Log.d("ForumAddFilePart", "ForumAddFilePart: $type")
                }
            }
        }
        forumViewModel.newForumFileList = files
    }



    MusicEducationTheme {
        Column(
            modifier = modifier.fillMaxWidth(1f),
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
        ) {




            // Add File Section
            Text(
                text = "添加文件",
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(5.dp),
                color = Color.Black
            )

            // Button to launch file picker for multiple files
            Button(
                onClick = {
                    filePickerLauncher.launch("*/*")
                },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(5.dp)
            ) {
                Text("点击选择文件",color = Color.White, style = MaterialTheme.typography.bodyMedium)
            }

            // Display selected files names
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
            ) {
                // Display added files
                files.forEach { fileInfo ->
                    ForumAddFileItem(navController = navController, file = fileInfo) {
                        files.remove(fileInfo)
                        forumViewModel.newForumFileList = files
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                }

            }


        }
    }



}

@Composable
fun ForumAddFileItem(
    modifier: Modifier = Modifier,
    navController: NavController,
    file: FileInfo,
    onDelete: () -> Unit = {}
){

    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            // 处理下载完成后的逻辑，可以根据需求进行操作
        }

    Row(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .background(Color.LightGray, RoundedCornerShape(5.dp))
            .padding(6.dp)
            .clickable {
                val uri = Uri.parse(file.url)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                launcher.launch(intent)
            },
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {

        // 如果type以image/开头，则显示图片图标
        if (file.fileType.startsWith("image/")) {
            Image(
                painter = painterResource(id = R.drawable.file_image),
                contentDescription = "图片文件",
                modifier = Modifier.size(40.dp)
            )
        } else if (file.fileType.startsWith("audio/")) {
            Image(
                painter = painterResource(id = R.drawable.file_voice),
                contentDescription = "音频文件",
                modifier = Modifier.size(40.dp)
            )
        } else if (file.fileType.startsWith("video/")) {
            Image(
                painter = painterResource(id = R.drawable.file_video),
                contentDescription = "视频文件",
                modifier = Modifier.size(40.dp)
            )
        } else if (file.fileType.startsWith("application/pdf")) {
            Image(
                painter = painterResource(id = R.drawable.file_pdf),
                contentDescription = "PDF文件",
                modifier = Modifier.size(40.dp)
            )
        } else if (file.fileType.startsWith("text/")) {
            Image(
                painter = painterResource(id = R.drawable.file_txt),
                contentDescription = "文本文件",
                modifier = Modifier.size(40.dp)
            )
        } else if(file.fileType.startsWith("application/vnd.ms-powerpoint")){
            Image(
                painter = painterResource(id = R.drawable.file_powerpoint),
                contentDescription = "PPT文件",
                modifier = Modifier.size(40.dp)
            )
        } else if(file.fileType.startsWith("application/vnd.ms-excel")){
            Image(
                painter = painterResource(id = R.drawable.file_excel),
                contentDescription = "Excel文件",
                modifier = Modifier.size(40.dp)
            )
        } else if(file.fileType.startsWith("application/msword")){
            Image(
                painter = painterResource(id = R.drawable.file_word),
                contentDescription = "Word文件",
                modifier = Modifier.size(40.dp)
            )

        } else if(file.fileType.startsWith("application/zip")){
            Image(
                painter = painterResource(id = R.drawable.file_zip),
                contentDescription = "压缩文件",
                modifier = Modifier.size(40.dp)
            )
        } else{
            Image(
                painter = painterResource(id = R.drawable.file_unknown),
                contentDescription = "未知文件",
                modifier = Modifier.size(40.dp)
            )
        }



        Text(
            text = file.fileName,
            modifier = Modifier
                .padding(horizontal = 3.dp)
                .weight(1f) // 占据剩余空间
                .height(IntrinsicSize.Max), // 保证高度相等
            color = Color.Black,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 2, // 设置最大行数为2，以支持自动换行
            overflow = TextOverflow.Ellipsis // 设置溢出时的处理方式为省略号
        )

        // 删除
        Image(
            painter = painterResource(id = R.drawable.delete),
            contentDescription = "删除",
            modifier = Modifier
                .size(20.dp)
                .clickable {
                    // 删除文
                    onDelete()
                }
        )

    }

}


@Composable
fun ForumAddBookLinkPart(
    navController: NavController,
    modifier: Modifier = Modifier,
    forumViewModel: ForumViewModel
){
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {

        Text(   
            text = "添加书籍链接",
            modifier = modifier
                .fillMaxWidth(0.9f)
                .padding(5.dp),
            color = Color.Black
        )
        for (i in 0 until forumViewModel.newForumNumberContent.size) {
            ForumAddBookLinkPartItem(navController = navController, bookLinkItem = forumViewModel.newForumNumberContent[i], modifier = Modifier.fillMaxWidth(0.9f), index = i)
        }
        Button(onClick = {
            forumViewModel.newForumNumberContent.add(mutableListOf(mutableListOf("0","请选择书籍","","0")))
        }) {
            Text(text = "添加新的备注",color = Color.White, style = MaterialTheme.typography.bodyMedium)
        }
    }

}


@Composable
fun ForumAddBookLinkPartItem(
    navController: NavController,
    modifier: Modifier = Modifier,
    bookLinkItem: MutableList<MutableList<String>>,
    index: Int
){
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        var showDropDown = remember { mutableListOf(mutableStateOf(false)) }
        var size = remember { mutableIntStateOf(bookLinkItem.size) }
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = "书籍链接部分${index+1}", modifier = modifier
            .fillMaxWidth(0.9f)
            .padding(5.dp),
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(5.dp))
        for (i in 0 until size.intValue) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(5.dp)
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                showDropDown[i].value = !showDropDown[i].value
                            }
                    ) {
                        Text(text = "链接类型:", modifier = Modifier
                            .width(100.dp)
                            .padding(5.dp),
                            color = Color.Black
                        )
                        Text(
                            text = reverseBookLink[bookLinkItem[i][0]] ?: "请选择链接类型",
                            modifier = Modifier
                                .height(40.dp)
                                .padding(5.dp)
                                .weight(1f),
                            color = Color.Black

                        )
                    }

                    DropdownMenu(
                        expanded = if(i<showDropDown.size) showDropDown[i].value else false,
                        onDismissRequest = { /*TODO*/ },
                        modifier = Modifier
                            .wrapContentHeight()
                    ) {
                        bookLink.forEach {
                            DropdownMenuItem(
                                onClick = {
                                    if(it.key == "原文" || it.key == "内部书籍链接"){
                                        bookLinkItem[i] = mutableListOf(it.value, "请选择书籍", "", "0")
                                    }else if(it.key == "外部书籍链接"){
                                        bookLinkItem[i] = mutableListOf(it.value, "请选择书籍", "", "0")
                                    }else{
                                        bookLinkItem[i] = mutableListOf(it.value, "", "")
                                    }
                                    showDropDown[i].value = false
                                },
                                text = {
                                    Text(text = it.key)
                                }
                            )
                        }
                    }
                }

                // 删除
                Button(
                    onClick = {
                        bookLinkItem.removeAt(i)
                        size.intValue--
                        showDropDown.removeAt(i)
                    },
                    modifier = Modifier
                        .width(80.dp)
                ) {
                    Text(text = "删除",color = Color.White, style = MaterialTheme.typography.bodyMedium)
                }
                Spacer(modifier = Modifier.height(5.dp))
            }
            Spacer(modifier = Modifier.height(5.dp))

            ForumAddBookLinkPartSubItem(navController = navController, bookLinkSubItem = bookLinkItem[i])
            Spacer(modifier = Modifier.height(5.dp))
        }

        if(size.intValue!=0){
            Button(onClick = {
                bookLinkItem.add(mutableListOf("0","请选择书籍","","0"))
                size.intValue++
                showDropDown.add(mutableStateOf(false))
            }) {
                Text(text = "添加新的备注项",color = Color.White, style = MaterialTheme.typography.bodyMedium)
            }
        }


    }


}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ForumAddBookLinkPartSubItem(
    navController: NavController,
    modifier: Modifier = Modifier,
    bookLinkSubItem: MutableList<String>
){
    Log.d("ForumAddBookLinkPartSubItem", "ForumAddBookLinkPartSubItem: $bookLinkSubItem")
//    if(bookLinkSubItem[0] == "0"|| bookLinkSubItem[0] == "4"){
//        // 原文
//        // data class InternelLink(val title: String,val bookName: String, val pageIndex: Int, val children: List<Directory> = emptyList()): Directory()
//        bookLinkSubItem.add("请选择书籍")
//        bookLinkSubItem.add("")
//        bookLinkSubItem.add("0")
//
//    }else if(bookLinkSubItem[0] == "1") {
//        // 书籍
//        // data class ExternalBookLink(val title: String, val bookId: String, val pageIndex: Int): Directory()
//        bookLinkSubItem.add("请选择书籍")
//        bookLinkSubItem.add("")
//        bookLinkSubItem.add("0")
//    }else if(bookLinkSubItem[0] == "2") {
//        // 外链
//        // data class ExternalURILink(val title: String, val url: String): Directory()
//        bookLinkSubItem.add("")
//        bookLinkSubItem.add("")
//    }
    var showDropDown = remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current

    MusicEducationTheme {
        if(bookLinkSubItem[0] == "0"|| bookLinkSubItem[0] == "1"|| bookLinkSubItem[0] == "4"){
            var titleState = remember { mutableStateOf(bookLinkSubItem[2]) }
            var pageIndexState = remember { mutableStateOf(bookLinkSubItem[3]) }
            Column(
                modifier = modifier
                    .fillMaxWidth(),
                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
            ) {
                // 书名（下拉框，四种书籍名称）
                Column(
                    modifier = Modifier.fillMaxWidth(0.9f)
                ) {
                    Text(
                        text = bookLinkSubItem[1],
                        modifier = Modifier
                            .height(40.dp)
                            .padding(5.dp)
                            .fillMaxWidth()
                            .clickable {
                                showDropDown.value = !showDropDown.value
                            },
                        color = Color.Black
                    )
                    DropdownMenu(
                        expanded = showDropDown.value,
                        onDismissRequest = { /*TODO*/ },
                        modifier = Modifier
                            .wrapContentHeight()
                    ) {
                        bookName.forEach {
                            DropdownMenuItem(
                                onClick = {
                                    bookLinkSubItem[1] = it
                                    showDropDown.value = false
                                },
                                text = {
                                    Text(text = it)
                                }
                            )
                        }
                    }
                }

                Column(
                    modifier = modifier
                        .fillMaxWidth(0.9f)
                        .wrapContentHeight()
                ) {
                    for (i in 2 until bookLinkSubItem.size) {
                        // 输入框
                        TextField(
                            value = if (i == 2) titleState.value else pageIndexState.value, // 输入框的值（状态)
                            onValueChange = {
                                // 当文本发生变化时更新状态
                                if (i == 2) {
                                    titleState.value = it
                                    bookLinkSubItem[2] = it
                                } else if (i == 3) {
                                    pageIndexState.value = it
                                    bookLinkSubItem[3] = it
                                }
                                Log.d(
                                    "ForumAddBookLinkPartSubItem",
                                    "ForumAddBookLinkPartSubItem: $bookLinkSubItem"
                                )
                            },
                            modifier = Modifier
                                .padding(5.dp)
                                .fillMaxWidth(),
                            label = {
                                if (i == 2) {
                                    Text(text = "标题")
                                } else {
                                    Text(text = "页码")
                                }
                            }, // 输入框的标签
                            keyboardOptions = KeyboardOptions.Default.copy(
                                imeAction = ImeAction.Done // 设置软键盘的操作按钮为 "Done"
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    // 当用户点击软键盘上的 "Done" 按钮时执行的操作
                                    keyboardController?.hide() // 隐藏软键盘
                                }
                            ),
                            textStyle = MaterialTheme.typography.bodyMedium // 设置文本样式
                        )
                    }
                }

                Spacer(modifier = Modifier.height(5.dp))

                Spacer(modifier = Modifier
                    .height(1.dp)
                    .background(Color.Gray)
                    .fillMaxWidth(0.9f))
                
            }
        }else{
            var titleState = remember { mutableStateOf(bookLinkSubItem[1]) }
            var urlState = remember { mutableStateOf(bookLinkSubItem[2]) }
            Column(
                modifier = modifier.fillMaxWidth(),
                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
            ) {
                // 输入框
                TextField(
                    value = titleState.value, // 输入框的值（状态)
                    onValueChange = {
                        // 当文本发生变化时更新状态
                        titleState.value = it
                        bookLinkSubItem[1] = it
                        Log.d("ForumAddBookLinkPartSubItem", "ForumAddBookLinkPartSubItem: $bookLinkSubItem")
                    },
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(0.9f),
                    label = {
                        Text(text = "标题")
                    }, // 输入框的标签
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done // 设置软键盘的操作按钮为 "Done"
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            // 当用户点击软键盘上的 "Done" 按钮时执行的操作
                            keyboardController?.hide() // 隐藏软键盘
                        }
                    ),
                    textStyle = MaterialTheme.typography.bodyMedium // 设置文本样式
                )
                TextField(
                    value = urlState.value, // 输入框的值（状态)
                    onValueChange = {
                        // 当文本发生变化时更新状态
                        urlState.value = it
                        bookLinkSubItem[2] = it
                        Log.d("ForumAddBookLinkPartSubItem", "ForumAddBookLinkPartSubItem: $bookLinkSubItem")
                    },
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(0.9f),
                    label = {
                        Text(text = "URL")
                    }, // 输入框的标签
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done // 设置软键盘的操作按钮为 "Done"
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            // 当用户点击软键盘上的 "Done" 按钮时执行的操作
                            keyboardController?.hide() // 隐藏软键盘
                        }
                    ),
                    textStyle = MaterialTheme.typography.bodyMedium // 设置文本样式
                )

                Spacer(modifier = Modifier.height(5.dp))

                Spacer(modifier = Modifier
                    .height(1.dp)
                    .background(Color.Gray)
                    .fillMaxWidth(0.9f))

            }
        }
    }
}

@Preview
@Composable
fun ForumAddBookLinkPartSubItemPreview(){
    var bookLinkSubItem = mutableListOf("0")
    val urlLinkSubItem = mutableListOf("2")
    Column {
        ForumAddBookLinkPartSubItem(navController = rememberNavController(), bookLinkSubItem = bookLinkSubItem)
        ForumAddBookLinkPartSubItem(navController = rememberNavController(), bookLinkSubItem = urlLinkSubItem)
        Button(onClick = {
            bookLinkSubItem = mutableListOf("2")
        }) {
            Text(text = "改变值")
        }
    }

}

@Preview
@Composable
fun ForumAddPagePreview(){
    ForumAddPage(navController = rememberNavController())
}

