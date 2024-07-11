package com.record.designsystem.component.videoplayer

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException.ERROR_CODE_IO_NETWORK_CONNECTION_FAILED
import androidx.media3.common.PlaybackException.ERROR_CODE_IO_NETWORK_CONNECTION_TIMEOUT
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.record.ui.lifecycle.ComposableLifecycle

@Composable
fun rememberExoPlayer(context: Context, videoUrl: String): ExoPlayer {
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(videoUrl))
            repeatMode = Player.REPEAT_MODE_ONE
            prepare()
        }
    }
    DisposableEffect(key1 = exoPlayer) {
        onDispose { exoPlayer.release() }
    }
    return exoPlayer
}

@androidx.annotation.OptIn(UnstableApi::class)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun VideoPlayer(videoId: Int, videoUrl: String, pagerState: PagerState, page: Int, onError: (String) -> Unit, onPlayVideo: (Int) -> Unit) {
    val context = LocalContext.current
    val exoPlayer = rememberExoPlayer(context, videoUrl)
    ComposableLifecycle { _, event ->
        when (event) {
            Lifecycle.Event.ON_START -> {
                exoPlayer.play()
            }

            Lifecycle.Event.ON_STOP -> exoPlayer.pause()
            Lifecycle.Event.ON_DESTROY -> exoPlayer.release()
            else -> {}
        }
    }

    LaunchedEffect(pagerState.currentPage) {
        exoPlayer.playWhenReady = pagerState.currentPage == page
        if (pagerState.currentPage != page) {
            exoPlayer.seekTo(0)
        }
    }

    PlayerListener(player = exoPlayer) { event ->
        when (event) {
            Player.EVENT_RENDERED_FIRST_FRAME -> { onPlayVideo(videoId) }
            Player.EVENT_PLAYER_ERROR -> {
                when (exoPlayer.playerError?.errorCode) {
                    ERROR_CODE_IO_NETWORK_CONNECTION_FAILED -> {
                        onError("네트워크 연결 실패")
                    }
                    ERROR_CODE_IO_NETWORK_CONNECTION_TIMEOUT -> {
                        onError("네트워크 타임아웃")
                    }
                    else -> {
                        onError("알 수 없는 오류")
                    }
                }
            }
        }
    }
    AndroidView(
        modifier = Modifier,
        factory = { viewContext ->
            PlayerView(viewContext).apply {
                useController = false
                player = exoPlayer
                resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
            }
        },
    )
}
