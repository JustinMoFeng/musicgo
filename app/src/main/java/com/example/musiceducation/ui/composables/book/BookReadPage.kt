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
    navController: NavController
) {
    var pageIdx by remember { mutableStateOf(pageIndex) }
    MusicEducationTheme {
        Scaffold(
            topBar = {
                MusicEducationBookReaderTopBar(
                    title = bookId,
                    onBack = {
                        navController.popBackStack()
                    },
                    onCatalog = {
                        navController.navigate(RouteConfig.ROUTE_BOOK_CATALOG+"/$bookId/$pageIdx")
                    }
                )
            },
            modifier = modifier.fillMaxSize(),
        ) {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(Color.White),
                contentAlignment = Alignment.TopCenter
            ){
                PdfReader(
                    modifier = modifier
                    .padding(it)
                    .fillMaxSize(),
                    bookId = bookIdToName[bookId] ?: "",
                    pageIndex = pageIdx,
                    onSwipe = {
                        Log.d("BookReadPage", "onSwipe: $it")
                        val nextPageIndex = pageIdx + it
                        if (nextPageIndex < 0 ) {
                            return@PdfReader
                        }
                        pageIdx = nextPageIndex
                    }
                )
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

@Composable
fun PdfReader(
    modifier: Modifier = Modifier,
    bookId: String,
    pageIndex: Int,
    onSwipe: (Int) -> Unit
) {
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    val context = androidx.compose.ui.platform.LocalContext.current

    var gestureDetected by remember { mutableStateOf(false) }
    var zoom by remember { mutableStateOf(1f) }

    var offset by remember { mutableStateOf(Offset(0f, 0f)) }

    // Detecting right-click or swipe gestures
    val gestureDetector = Modifier.pointerInput(Unit) {
        detectTransformGestures { _, pan, gestureZoom, offsets ->
            val isSwiping = pan.x.absoluteValue > 100

            if (gestureZoom != null && gestureZoom.absoluteValue > 0.1f) {
                // 缩放图片
                zoom *= gestureZoom
                if(zoom < 0.8f) {
                    zoom = 0.8f
                }else if(zoom > 3f) {
                    zoom = 3f
                }

                // 根据缩放比例调整偏移，使图像在中心
                offset = Offset(offset.x * zoom, offset.y * zoom)
            }

            // Check if the gesture has already been detected
            if (!gestureDetected && isSwiping) {
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

    // 使用 LaunchedEffect 在组合启动时进行初始化
    LaunchedEffect(bookId,bitmap,context) {
        // 打开 PDF 文件
        val pdfRenderer = PdfRenderer(ParcelFileDescriptor.open(getBookFromAssets(context, bookId), ParcelFileDescriptor.MODE_READ_ONLY))

        // 打开指定页
        val pdfPage = pdfRenderer.openPage(pageIndex)

        // 创建 Bitmap
        val bitmap2 = Bitmap.createBitmap(pdfPage.width, pdfPage.height, Bitmap.Config.ARGB_8888)

        // 渲染 PDF 页面到 Bitmap
        pdfPage.render(bitmap2, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)

        bitmap = bitmap2

        // 关闭打开的页面和渲染器
        pdfPage.close()
        pdfRenderer.close()
    }

    MusicEducationTheme {
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

@Preview
@Composable
fun BookReadPagePreview() {
    BookReadPage(bookId = "hamlet.pdf", navController = rememberNavController())
}

@Preview
@Composable
fun PdfReaderPreview() {
    Box(modifier = Modifier.fillMaxSize()) {
        PdfReader(bookId = "hamlet.pdf", pageIndex = 0, onSwipe = {})
    }
}
