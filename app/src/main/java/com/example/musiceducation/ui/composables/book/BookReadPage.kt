package com.example.musiceducation.ui.composables.book

import android.content.Context
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.musiceducation.config.RouteConfig
import com.example.musiceducation.ui.composables.common.MusicEducationBookReaderTopBar
import com.example.musiceducation.ui.theme.MusicEducationTheme
import java.io.File
import kotlin.math.absoluteValue
import android.graphics.Matrix
import android.widget.Space
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer


import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.ui.unit.dp
import com.example.musiceducation.ui.viewModels.BookCatalogViewModel

val bookIdToName = mapOf(
    "哈姆雷特" to "hamlet.pdf",
    "音乐理论基础" to "music_basic.pdf",
    "选择必修5 音乐基础理论" to "music_education.pdf",
    "基本乐理教程" to "basic_music_education.pdf",
    "基本乐理通用教材" to "basic_music_common_education.pdf",
)



@Composable
fun BookReadPage(
    modifier: Modifier = Modifier,
    bookId: String,
    pageIndex: Int = 0,
    navController: NavController,
    bookCatalogViewModel: BookCatalogViewModel
) {
    var pageIdx by remember { mutableStateOf(pageIndex) }
    var showSidebar by remember { mutableStateOf(false) }
    //初始化为空位图
    var bitmap by remember { mutableStateOf<Bitmap?>(getBitMapFromBook(navController.context, bookIdToName[bookId] ?: "", pageIdx)) }
    MusicEducationTheme {
        Scaffold(
            topBar = {
                MusicEducationBookReaderTopBar(
                    title = bookId,
                    onBack = {
                        navController.popBackStack()
                    },
                    onCatalog = {
                        showSidebar = !showSidebar
                    }
                )
            },
            modifier = modifier.fillMaxSize(),
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(it)
                    .background(Color.White),
                horizontalArrangement = Arrangement.Center
            ){
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .graphicsLayer(
                            scaleX = if(showSidebar) 0.8f else 1f,
                            scaleY = if(showSidebar) 0.8f else 1f
                        ),
                    verticalArrangement = Arrangement.Center
                ) {

                    PdfReader(
                        modifier = modifier
                            .fillMaxSize(),
                        bookId = bookIdToName[bookId] ?: "",
                        bitmap = bitmap,
                        pageIndex = pageIdx,
                        context = navController.context,
                        onSwipe = {
                            Log.d("BookReadPage", "onSwipe: $it")
                            val nextPageIndex = pageIdx + it
                            if (nextPageIndex < 0 ) {
                                return@PdfReader
                            }
                            getBitMapFromBook(navController.context, bookIdToName[bookId] ?: "", nextPageIndex)?.let {its ->
                                pageIdx = nextPageIndex
                                bitmap = its
                            }

                            pageIdx = nextPageIndex
                        }
                    )
                }

                if(showSidebar) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .background(Color.Gray)
                    ) {
                        // 在这里放置目录页面的内容
                        BookCatalogPageContent(
                            catalog = bookToCatalog[bookId] ?: emptyList(),
                            modifier = Modifier.fillMaxSize(),
                            navController = navController,
                            bookCatalogViewModel = bookCatalogViewModel
                        )
                    }
                }


            }

        }

    }




}

// 获取assets文件夹中的书籍
fun getBookFromAssets(context: Context, bookId: String): File {
    val assetManager = context.assets
    val file = File(context.cacheDir, bookId)
    if (!file.exists()) {
        file.createNewFile()
        val inputStream = assetManager.open(bookId)
        val outputStream = file.outputStream()
        inputStream.copyTo(outputStream)
        inputStream.close()
        outputStream.close()
    }
    return file
}

fun getBitMapFromBook(context: Context, bookId: String, pageIndex: Int): Bitmap? {
    val displayMetrics = context.resources.displayMetrics
    val density = displayMetrics.density

    // 打开 PDF 文件
    val pdfRenderer = PdfRenderer(ParcelFileDescriptor.open(getBookFromAssets(context, bookId), ParcelFileDescriptor.MODE_READ_ONLY))

    // 打开指定页
    val pdfPage = pdfRenderer.openPage(pageIndex)

    // 计算位图宽高，考虑屏幕密度
    val bitmapWidth = displayMetrics.widthPixels
    val bitmapHeight = displayMetrics.heightPixels

    // 创建 Bitmap
    val bitmap2 = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888)
    // 给bitmap2 上红色

    // 计算缩放比例，以适应屏幕密度
    val scale = bitmapWidth.toFloat() / pdfPage.width


    // 渲染 PDF 页面到 Bitmap，考虑缩放
    val matrix = Matrix()
    matrix.setScale(scale, scale)

    pdfPage.render(bitmap2, null,matrix, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)

    // 关闭打开的页面和渲染器
    pdfPage.close()
    pdfRenderer.close()

    return bitmap2
}

@Composable
fun PdfReader(
    modifier: Modifier = Modifier,
    bookId: String,
    pageIndex: Int,
    bitmap: Bitmap? = null,
    onSwipe: (Int) -> Unit,
    context: Context = androidx.compose.ui.platform.LocalContext.current
) {

    var zoom by remember { mutableStateOf(1f) }

    var offset by remember { mutableStateOf(Offset(0f, 0f)) }
    val displayMetrics = context.resources.displayMetrics
    var gestureDetected by remember { mutableStateOf(false) }
    var isSwiping by remember { mutableStateOf(false) }


    // Detecting right-click or swipe gestures
    val gestureDetector = Modifier.pointerInput(Unit) {
        detectTransformGestures(
            panZoomLock = false
        ) { _, pan, zoomChange, _ ->
            isSwiping = pan.x.absoluteValue > 30
            if (zoomChange != 1f) {

                zoom *= zoomChange
            }
            if(zoom < 1f) {
                zoom = 1f
            }else if(zoom >3f){
                zoom = 3f
            }

            var maxOffsetX = 0f
            var maxOffsetY = 0f


            // 判断移动距离是否超出边界，左边界不能小于0，右边界不能大于屏幕宽度
            if (bitmap != null) {
                val bitmapWidth = (bitmap.width * zoom).toInt()
                val bitmapHeight = (bitmap.height * zoom).toInt()
                val screenWidth = displayMetrics.widthPixels
                val screenHeight = displayMetrics.heightPixels
                Log.d("PdfReader", "zoom: $zoom,bitmapWidth: $bitmapWidth, bitmapHeight: $bitmapHeight, screenWidth: $screenWidth, screenHeight: $screenHeight")
                maxOffsetX = (bitmapWidth - screenWidth).toFloat()
                maxOffsetY = (bitmapHeight - screenHeight  ).toFloat()
                offset = Offset(offset.x + pan.x, offset.y + pan.y)
                offset = Offset(offset.x.coerceIn(-maxOffsetX/2,maxOffsetX/2), offset.y.coerceIn(-maxOffsetY/2,maxOffsetY/2))
            }

            //当zoom==1时实现翻页
            if (!gestureDetected && isSwiping && zoom == 1f) {
                // You can adjust the sensitivity or add conditions based on the pan or zoom values
                if (pan.x > 0) {
                    // Right swipe detected, move to the next page
                    onSwipe(-1)
                } else {
                    // Left swipe detected, move to the previous page
                    onSwipe(1)
                }
                gestureDetected = true
            }

            if (!isSwiping) {
                gestureDetected = false
            }



        }

    }



    MusicEducationTheme {
        Box(modifier = modifier.fillMaxSize()) {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .then(gestureDetector)
                    .graphicsLayer(
                        scaleX = zoom,
                        scaleY = zoom,
                        translationX = offset.x,
                        translationY = offset.y
                    ),
                contentAlignment = Alignment.TopCenter
            ){
                bitmap?.let {
                    Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = null,
                        modifier = modifier.fillMaxWidth(),
                        contentScale = ContentScale.Fit,
                        alignment = Alignment.TopCenter,
                    )
                }
            }
        }


    }

}

//@Preview
//@Composable
//fun BookReadPagePreview() {
//    BookReadPage(bookId = "hamlet.pdf", navController = rememberNavController())
//}

@Preview
@Composable
fun PdfReaderPreview() {
    Box(modifier = Modifier.fillMaxSize()) {
        PdfReader(bookId = "hamlet.pdf", pageIndex = 0, onSwipe = {})
    }
}
