package com.example.musiceducation.ui.composables.common

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.musiceducation.ui.theme.MusicEducationTheme


@Composable
fun WebViewPage(
    url: String,
    title: String,
    navController: NavController
) {
    MusicEducationTheme {
        Scaffold(
            topBar = {
                MusicEducationOnlyBackTopBar(title = title, onBack = {
                    navController.popBackStack()
                })
            }
        ) {
            WebViewPageContent(
                modifier = Modifier.padding(it),
                url = url
            )
        }
    }

}

@Composable
fun WebViewPageContent(
    modifier: Modifier = Modifier,
    url: String
) {
    MusicEducationTheme {
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    settings.javaScriptEnabled = true
                    webViewClient = CustomWebViewClient(context)
                    loadUrl(url)
                }

            },
            update = {
                it.loadUrl(url)
            },
            modifier = modifier.fillMaxSize()
        )
    }

}

class CustomWebViewClient(private val context: Context) : WebViewClient() {
    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        val url = request?.url?.toString()

        if (url?.startsWith("bilibili://video/") == true) {
            // Custom handling for bilibili://video/ URL
            launchBilibiliApp(url)
            return true // The URL is handled by your code, prevent the WebView from loading it
        }

        // If it's not a custom scheme, let the WebView handle the URL
        return super.shouldOverrideUrlLoading(view, request)
    }

    private fun launchBilibiliApp(url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            if (intent.resolveActivity(context.packageManager) != null) {
                context.startActivity(intent)
            } else {
                // Handle the case where the Bilibili app is not installed
                // Alternatively, redirect to the Bilibili website or take another action
            }
        } catch (e: ActivityNotFoundException) {
            // Handle the case where no activity can handle the intent
        }
    }
}