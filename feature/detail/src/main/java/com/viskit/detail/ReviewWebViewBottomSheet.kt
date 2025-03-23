package com.viskit.detail

import android.content.Context
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.record.designsystem.theme.RecordyTheme
import com.record.ui.lifecycle.LaunchedEffectWithLifecycle
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewWebViewBottomSheeet(
    modifier: Modifier = Modifier,
    platformId: Long,
    isShowBottomSheet: Boolean,
    onDismiss: () -> Unit,
) {
    val lazyListState = rememberLazyListState(initialFirstVisibleItemIndex = 0)
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val context = LocalContext.current
    val webView = rememberWebview(context = context, platformId = platformId)

    LaunchedEffectWithLifecycle {
        if (isShowBottomSheet) {
            coroutineScope.launch {
                sheetState.show()
            }
        }
    }
    if (isShowBottomSheet) {
        ModalBottomSheet(
            shape = RoundedCornerShape(12.dp),
            onDismissRequest = {
                coroutineScope.launch {
                    sheetState.hide()
                }.invokeOnCompletion {
                    onDismiss()
                }
            },
            sheetState = sheetState,
            dragHandle =
            {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(RecordyTheme.colors.white),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    BottomSheetDefaults.DragHandle()
                }
            },
        ) {
            Box(
                modifier = Modifier
                    .background(RecordyTheme.colors.white)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                LazyColumn(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    userScrollEnabled = true,
                    state = lazyListState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(RecordyTheme.colors.white),
                ) {
                    item {
                        AndroidView(
                            factory = { context ->
                                WebView(context).apply {
                                    webViewClient = WebViewClient()
                                    webChromeClient = WebChromeClient()

                                    isNestedScrollingEnabled = true
                                    layoutParams = ViewGroup.LayoutParams(
                                        ViewGroup.LayoutParams.MATCH_PARENT,
                                        ViewGroup.LayoutParams.MATCH_PARENT,
                                    )
                                    settings.apply {
                                        javaScriptEnabled = true
                                        domStorageEnabled = true
                                        loadWithOverviewMode = true
                                        useWideViewPort = true
                                        cacheMode = WebSettings.LOAD_NO_CACHE
                                        setSupportZoom(true)
                                    }
                                    webViewClient = WebViewClient()
                                    loadUrl("https://place.map.kakao.com/m/$platformId#review")
                                }
                            },
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun rememberWebview(context: Context, platformId: Long): WebView {
    val webView = remember {
        WebView(context).apply {
            webViewClient = WebViewClient()
            webChromeClient = WebChromeClient()

            isNestedScrollingEnabled = true

            settings.apply {
                javaScriptEnabled = true
                domStorageEnabled = true
                loadWithOverviewMode = true
                useWideViewPort = true
                cacheMode = WebSettings.LOAD_NO_CACHE
                setSupportZoom(true)
            }
            webViewClient = WebViewClient()
            loadUrl("https://place.map.kakao.com/m/$platformId#review")
        }
    }

    return webView
}
