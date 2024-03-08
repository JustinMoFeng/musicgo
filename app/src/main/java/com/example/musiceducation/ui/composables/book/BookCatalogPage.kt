package com.example.musiceducation.ui.composables.book

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.musiceducation.R
import com.example.musiceducation.config.RouteConfig
import com.example.musiceducation.ui.composables.common.Directory
import com.example.musiceducation.ui.composables.common.MusicEducationOnlyBackTopBar
import com.example.musiceducation.ui.viewModels.BookCatalogViewModel
import com.example.musiceducation.utils.KeyValueFileStorage
import com.example.musiceducation.utils.Serialize
import java.net.URLEncoder


val HamletCatalog = listOf(
    Directory.InternelLink("《哈姆雷特》简介","哈姆雷特", 0, listOf(
        Directory.InternelLink("作品简介", "哈姆雷特", 0),
        Directory.InternelLink("作者简介","哈姆雷特",  0),
    )),
    Directory.InternelLink("第一幕", "哈姆雷特", 5, listOf(
        Directory.InternelLink("第一场 艾尔西诺。城堡前的露台","哈姆雷特",  5),
        Directory.InternelLink("第二场 城堡中的大厅","哈姆雷特",  9),
        Directory.InternelLink("第三场 波洛涅斯家中一室", "哈姆雷特", 13),
        Directory.InternelLink("第四场 露台","哈姆雷特",  15),
        Directory.InternelLink("第五场 露台的另一部分","哈姆雷特",  16)
    )),
    Directory.InternelLink("第二幕","哈姆雷特",  19, listOf(
        Directory.InternelLink("第一场 波洛涅斯家中一室","哈姆雷特",  19),
        Directory.InternelLink("第二场 城堡中一室", "哈姆雷特", 21, listOf(
            Directory.InternelLink("原文", "哈姆雷特", 21),
            Directory.InternelLink("关联段落","哈姆雷特", 67),
            Directory.ExternalURILink("【话剧】哈姆雷特（带字幕）北京人艺 2008", "https://b23.tv/3L912ls"),
            Directory.ExternalURILink("一分钟讲完《哈姆雷特》", "https://b23.tv/5g3uEvA")
        )),
    )),
    Directory.InternelLink("第三幕","哈姆雷特",  31),
    Directory.InternelLink("第四幕","哈姆雷特",  46),
    Directory.InternelLink("第五幕","哈姆雷特",  58),
    Directory.InternelLink("注释", "哈姆雷特", 70),
)

val MusicEducationCatalog = listOf(
    Directory.InternelLink("第一单元 乐音与记谱","选择必修5 音乐基础理论",12),
    Directory.InternelLink("第二单元 节奏与节拍","选择必修5 音乐基础理论",  26),
    Directory.InternelLink("第三单元 音程与和弦","选择必修5 音乐基础理论",  40, listOf(
        Directory.InternelLink("第一节 自然音程及转位","选择必修5 音乐基础理论",  41, listOf(
            Directory.InternelLink("原文","选择必修5 音乐基础理论",  41),
            Directory.InternelLink("关联段落 《音乐基础理论》","选择必修5 音乐基础理论",  41, listOf(
                Directory.ExternalBookLink("第一节 音程 旋律音程 和声音程","音乐理论基础",  103),
                Directory.ExternalBookLink("第二节 音程的级数和音数","音乐理论基础",  104),
                Directory.ExternalBookLink("第三节 自然音程和变化音程","音乐理论基础",  105),
                Directory.ExternalBookLink("第五节 音程的转位","音乐理论基础",  109),
                Directory.ExternalBookLink("第十节 调式中的音程","音乐理论基础",  117)
            )),
            Directory.InternelLink("关联段落 《基本乐理教程》","选择必修5 音乐基础理论",  41, listOf(
                Directory.ExternalBookLink("第一节 旋律音程与和声音程","基本乐理教程",  62),
                Directory.ExternalBookLink("第二节 音程的度数与性质","基本乐理教程",  63),
                Directory.ExternalBookLink("第三节 音程的扩大与缩小","基本乐理教程",  65),
                Directory.ExternalBookLink("第六节 音程的转位","基本乐理教程",  68),
            )),
            Directory.InternelLink("关联段落 《基本乐理通用教材》","选择必修5 音乐基础理论",  41, listOf(
                Directory.ExternalBookLink("第一节 什么是音程","基本乐理通用教材",  68),
                Directory.ExternalBookLink("第二节 音程的名称与标记","基本乐理通用教材",  69),
                Directory.ExternalBookLink("第四节 自然音程与变化音程","基本乐理通用教材",  74),
                Directory.ExternalBookLink("第四节 自然音程与变化音程","基本乐理通用教材",  75),
                Directory.ExternalBookLink("第七节 音程的转位","基本乐理通用教材",  79),
            )),
            Directory.InternelLink("视频关联","选择必修5 音乐基础理论",  41, listOf(
                Directory.ExternalURILink("音程全部内容","https://b23.tv/GfxwRq8"),
                Directory.ExternalURILink("一、音程的概念","https://b23.tv/PXRyFOA"),
                Directory.ExternalURILink("一、音程的概念","https://b23.tv/HJVOjjN"),
                Directory.ExternalURILink("二、根音、冠音","https://b23.tv/PXRyFOA"),
                Directory.ExternalURILink("二、度数、根音、冠音","https://b23.tv/HJVOjjN"),
                Directory.ExternalURILink("二、度数","https://b23.tv/78mdrt2"),
                Directory.ExternalURILink("三、音数","https://b23.tv/BdTn5Dl"),
                Directory.ExternalURILink("四、自然音程与音程名称分类","https://b23.tv/1gBuG17"),
                Directory.ExternalURILink("四、自然音程与音程名称分类","https://b23.tv/BctzAeT"),
                Directory.ExternalURILink("六、音程转位","https://b23.tv/M7RP5tr")
            )),

        )),
        Directory.InternelLink("第二节 变化音程、等音程与复音程","选择必修5 音乐基础理论",  45, listOf(
            Directory.InternelLink("原文","选择必修5 音乐基础理论",  45),
            Directory.InternelLink("关联段落 《音乐基础理论》","选择必修5 音乐基础理论",  45, listOf(
                Directory.ExternalBookLink("第三节 自然音程和变化音程","音乐理论基础",  105),
                Directory.ExternalBookLink("第四节 单音程与复音程","音乐理论基础",  108),
                Directory.ExternalBookLink("第七节 等音程","音乐理论基础",  112),
                Directory.ExternalBookLink("第八节 协和音程与不协和音程","音乐理论基础",  114),
                Directory.ExternalBookLink("第十一节 不协和音程的解决","音乐理论基础",  123),
            )),
            Directory.InternelLink("关联段落 《基本乐理教程》","选择必修5 音乐基础理论",  45, listOf(
                Directory.ExternalBookLink("第四节 单音程与复音程","基本乐理教程",  66),
                Directory.ExternalBookLink("第五节 等音程","基本乐理教程",  67),
                Directory.ExternalBookLink("第八节 协和音程与不协和音程","基本乐理教程",  70),
            )),
            Directory.InternelLink("关联段落 《基本乐理通用教材》","选择必修5 音乐基础理论",  45, listOf(
                Directory.ExternalBookLink("第三节 单音程与复音程","基本乐理通用教材",  73),
                Directory.ExternalBookLink("第四节 自然音程与变化音程","基本乐理通用教材",  74),
                Directory.ExternalBookLink("第六节 协和音程与不协和音程","基本乐理通用教材",  78),
                Directory.ExternalBookLink("第八节 等音程","基本乐理通用教材",  80),
            )),
            Directory.InternelLink("视频关联","选择必修5 音乐基础理论",  45, listOf(
                Directory.ExternalURILink("一、变化音程","https://b23.tv/b68O4yR"),
                Directory.ExternalURILink("一、变化音程","https://b23.tv/E9yyqpI"),
                Directory.ExternalURILink("二、等音程","https://b23.tv/R1C2UF1"),
                Directory.ExternalURILink("二、等音程","https://b23.tv/nv6iDbi"),
                Directory.ExternalURILink("三、协和音程与不协和音程","https://b23.tv/sVWkOct"),
                Directory.ExternalURILink("四、复音程","https://b23.tv/J0iOji0"),
            ))
        )),
        Directory.InternelLink("第三节 三和弦及转位","选择必修5 音乐基础理论",  48),
    )),
    Directory.InternelLink("第四单元 调试、音阶与调关系","选择必修5 音乐基础理论",  54),
    Directory.InternelLink("第五单元 记号、术语与译谱移调","选择必修5 音乐基础理论",  66),
)

val bookToCatalog = mapOf(
    "哈姆雷特" to HamletCatalog,
    "选择必修5 音乐基础理论" to MusicEducationCatalog,
)




@Composable
fun BookCatalogPage(
    bookName: String,
    currentPage: Int,
    navController: NavController,
    bookCatalogViewModel: BookCatalogViewModel
) {
    var catalog = emptyList<Directory>()
    if(KeyValueFileStorage.loadValueForKey(navController.context, bookName) == null) {
        val bookCatalog = bookToCatalog[bookName] ?: emptyList()
        catalog = bookCatalog
        KeyValueFileStorage.saveKeyValue(navController.context, bookName, Serialize.serializeList(bookCatalog))
    }else {
        val bookCatalog = KeyValueFileStorage.loadValueForKey(navController.context, bookName)
        catalog = Serialize.deserializeList<Directory>(bookCatalog!!)

    }
    Scaffold(
        topBar = {
            MusicEducationOnlyBackTopBar(title = bookName, onBack = {
                navController.popBackStack()
            })
        }
    ) {
        BookCatalogPageContent(catalog = catalog, modifier = Modifier.padding(it), navController = navController, bookCatalogViewModel = bookCatalogViewModel)
    }
}


@Composable
fun BookCatalogPageContent(
    catalog: List<Directory>,
    modifier: Modifier = Modifier,
    navController: NavController,
    bookCatalogViewModel: BookCatalogViewModel
) {


    LazyColumn(
        modifier = modifier
            .background(Color.White)
            .fillMaxHeight()
            .padding(horizontal = 16.dp),
    ) {
        itemsIndexed(catalog) { index, item ->
            DirectoryItem(item, navController, bookCatalogViewModel)
        }
    }
}

@Composable
fun DirectoryItem(
    directoryItem: Directory,
    navController: NavController,
    bookCatalogViewModel: BookCatalogViewModel
) {
    var isExpanded = remember{ mutableStateOf(false)}
//    if(directoryItem is Directory.InternelLink) {
//        Log.d("DirectoryItem", "DirectoryItem: ${bookCatalogViewModel.expandedStates[directoryItem.title]}")
//        isExpanded = remember {
//            val expanded = bookCatalogViewModel.expandedStates[directoryItem.title] ?: mutableStateOf(false)
//            expanded
//        }
//        bookCatalogViewModel.expandedStates[directoryItem.title] = isExpanded
//
//
//    }

//    DisposableEffect(Unit) {
//        // 在组合启动时执行的操作
//
//        Log.d("DirectoryItem", "DisposableEffect")
//
//        // 在组合被丢弃时执行的操作
//        onDispose {
//            Log.d("DirectoryItem", "onDispose")
//        }
//    }

    Column {
        Row(
            modifier = Modifier
                .clickable {
                    isExpanded.value = !isExpanded.value
                    if (directoryItem is Directory.InternelLink) {
//                        bookCatalogViewModel.expandedStates[directoryItem.title] = isExpanded
//                        Log.d("DirectoryItem", "DirectoryItem: ${bookCatalogViewModel.expandedStates[directoryItem.title]}")
                    }
                    if (directoryItem is Directory.InternelLink && directoryItem.children.isEmpty()) {
                        navController.popBackStack()
                        navController.popBackStack()
                        navController.navigate(RouteConfig.ROUTE_BOOK_READ + "/${directoryItem.bookName}/${directoryItem.pageIndex}")
                    }
                }
                .padding(16.dp),
            verticalAlignment = androidx.compose.ui.Alignment.Top,
        ) {
            // 根据目录项的类型显示不同的图标
            if (directoryItem is Directory.InternelLink && directoryItem.children.isNotEmpty()) {
                Image(
                    painter = if (isExpanded.value) painterResource(id = R.drawable.down_arrow) else painterResource(id = R.drawable.right_arrow),
                    contentDescription = null,
                    modifier = Modifier
                        .size(15.dp)
                        .padding(end = 4.dp)
                )
                Text(
                    text = directoryItem.title,
                    modifier = Modifier
                        .weight(1f)  // 让文字占据剩余空间
                        .padding(horizontal = 8.dp),
                    color = Color.Black,
                    style = MaterialTheme.typography.bodySmall
                )
            }else if (directoryItem is Directory.InternelLink){
                Text(
                    text = directoryItem.title,
                    modifier = Modifier
                        .weight(1f)  // 让文字占据剩余空间
                        .padding(start = 19.dp, end = 8.dp),
                    color = Color.Black,
                    style = MaterialTheme.typography.bodySmall
                )
            }


        }

        if (isExpanded.value && directoryItem is Directory.InternelLink && directoryItem.children.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .wrapContentHeight()
                    .fillMaxWidth()
            ) {
                directoryItem.children.forEach { child ->
                    if(child is Directory.InternelLink) {
                        DirectoryItem(child, navController, bookCatalogViewModel)
                    }else if (child is Directory.ExternalBookLink) {
                        ExternalBookLinkItem(child, navController)
                    }else if (child is Directory.ExternalURILink) {
                        ExternalURILinkItem(child, navController)
                    }
                }
            }
        }
    }
}

@Composable
fun DirectoryItemFromDirectory(
    directoryItem: Directory,
    navController: NavController,
) {
    var isExpanded = remember{ mutableStateOf(false)}



    Column {
        Row(
            modifier = Modifier
                .clickable {
                    isExpanded.value = !isExpanded.value
                    if (directoryItem is Directory.InternelLink && directoryItem.children.isEmpty()) {
                        navController.navigate(RouteConfig.ROUTE_BOOK_READ + "/${directoryItem.bookName}/${directoryItem.pageIndex}")
                    }
                }
                .padding(16.dp),
            verticalAlignment = androidx.compose.ui.Alignment.Top,
        ) {
            // 根据目录项的类型显示不同的图标
            if (directoryItem is Directory.InternelLink && directoryItem.children.isNotEmpty()) {
                Image(
                    painter = if (isExpanded.value) painterResource(id = R.drawable.down_arrow) else painterResource(id = R.drawable.right_arrow),
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp)
                        .padding(end = 4.dp)
                )
                Text(
                    text = directoryItem.title,
                    modifier = Modifier
                        .weight(1f)  // 让文字占据剩余空间
                        .padding(horizontal = 8.dp),
                    color = Color.Black,
                )
            }else if (directoryItem is Directory.InternelLink){
                Text(
                    text = directoryItem.title,
                    modifier = Modifier
                        .weight(1f)  // 让文字占据剩余空间
                        .padding(start = 28.dp, end = 8.dp),
                    color = Color.Black
                )
            }


        }

        if (isExpanded.value && directoryItem is Directory.InternelLink && directoryItem.children.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .wrapContentHeight()
                    .fillMaxWidth()
            ) {
                directoryItem.children.forEach { child ->
                    if(child is Directory.InternelLink) {
                        DirectoryItemFromDirectory(child, navController)
                    }else if (child is Directory.ExternalBookLink) {
                        SecondExternalBookLinkItem(child, navController)
                    }else if (child is Directory.ExternalURILink) {
                        SecondExternalURILinkItem(child, navController)
                    }
                }
            }
        }
    }
}

@Composable
fun InternelBookLinkItem(item: Directory.InternelLink) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {
                // 打开内部书籍
            },
        verticalAlignment = androidx.compose.ui.Alignment.Top,
    ){
        Spacer(modifier = Modifier
            .size(20.dp)
            .padding(end = 4.dp))

        Text(
            text = item.title,
            modifier = Modifier
                .weight(1f),
            color = Color.Black
        )
    }
}

@Composable
fun SecondExternalBookLinkItem(
    item: Directory.ExternalBookLink,
    navController: NavController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {
                navController.navigate(RouteConfig.ROUTE_BOOK_READ + "/${item.bookId}/${item.pageIndex}")
            },
        verticalAlignment = androidx.compose.ui.Alignment.Top,
    ){
        Text(
            text = item.title,
            modifier = Modifier
                .weight(1f)
                .padding(start = 28.dp,end = 4.dp),
            color = Color.DarkGray,
        )
    }

}

@Composable
fun ExternalBookLinkItem(
    item: Directory.ExternalBookLink,
    navController: NavController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {
                navController.navigate(RouteConfig.ROUTE_BOOK_READ + "/${item.bookId}/${item.pageIndex}")
            },
        verticalAlignment = androidx.compose.ui.Alignment.Top,
    ){
        Text(
            text = item.title,
            modifier = Modifier
                .weight(1f)
                .padding(start = 10.dp,end = 4.dp),
            color = Color.DarkGray,
            style = MaterialTheme.typography.bodySmall
        )
    }

}

@Composable
fun SecondExternalURILinkItem(
    item: Directory.ExternalURILink,
    navController: NavController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                val encodedURL = URLEncoder.encode(item.url, "utf-8")
                navController.navigate(RouteConfig.COMMON_WEBVIEW + "/$encodedURL/${item.title}")
            },
        verticalAlignment = androidx.compose.ui.Alignment.Top,
    ){
        Spacer(modifier = Modifier
            .size(20.dp)
            .padding(end = 4.dp))

        Text(
            text = item.title,
            modifier = Modifier
                .padding(16.dp),
            color = Color.Blue
        )
    }
}

@Composable
fun ExternalURILinkItem(
    item: Directory.ExternalURILink,
    navController: NavController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                val encodedURL = URLEncoder.encode(item.url, "utf-8")
                navController.navigate(RouteConfig.COMMON_WEBVIEW + "/$encodedURL/${item.title}")
            },
        verticalAlignment = androidx.compose.ui.Alignment.Top,
    ){

        Text(
            text = item.title,
            modifier = Modifier
                .padding(16.dp),
            color = Color.Blue,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Preview
@Composable
fun BookCatalogPageContentPreview() {
    BookCatalogPageContent(catalog = HamletCatalog, navController = rememberNavController(), bookCatalogViewModel = BookCatalogViewModel())
}


