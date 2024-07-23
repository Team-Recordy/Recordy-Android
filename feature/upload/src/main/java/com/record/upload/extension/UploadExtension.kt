package com.record.upload.extension

import android.content.Context
import android.net.Uri
import android.util.Log
import com.abedelazizshe.lightcompressorlibrary.CompressionListener
import com.abedelazizshe.lightcompressorlibrary.VideoCompressor
import com.abedelazizshe.lightcompressorlibrary.VideoQuality
import com.abedelazizshe.lightcompressorlibrary.config.Configuration
import com.abedelazizshe.lightcompressorlibrary.config.SaveLocation
import com.abedelazizshe.lightcompressorlibrary.config.SharedStorageConfiguration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun formatDuration(durationMillis: Long): String {
    val minutes = (durationMillis / 1000) / 60
    val seconds = (durationMillis / 1000) % 60
    return String.format("%d:%02d", minutes, seconds)
}

fun compressVideo(context: Context, videoUri: Uri, name: String, onSuccess: (String?) -> Unit) {
    CoroutineScope(Dispatchers.IO).launch {
        VideoCompressor.start(
            context = context,
            uris = listOf(videoUri),
            isStreamable = false,
            sharedStorageConfiguration = SharedStorageConfiguration(
                saveAt = SaveLocation.movies,
                subFolderName = "compressed_videos",
            ),
            configureWith = Configuration(
                quality = VideoQuality.MEDIUM,
                isMinBitrateCheckEnabled = true,
                videoBitrateInMbps = 5,
                disableAudio = false,
                keepOriginalResolution = false,
                videoNames = listOf(name),
            ),
            listener = object : CompressionListener {
                override fun onProgress(index: Int, percent: Float) {
                    // 압축 진행 상황 업데이트
                    Log.d("video onProgress", "$index , $percent")
                }

                override fun onStart(index: Int) {
                    // 압축 시작 시 호출
                    Log.d("video onStart", "$index")
                }

                override fun onSuccess(index: Int, size: Long, path: String?) {
                    // 압축 성공 시 호출
                    Log.d("video onSuccess", "$index $size $path")
                    onSuccess(path)
                }

                override fun onFailure(index: Int, failureMessage: String) {
                    // 압축 실패 시 호출
                    Log.d("video onFailure", "onFailure")
                }

                override fun onCancelled(index: Int) {
                    // 압축 취소 시 호출
                    Log.d("video onCancelled", "onCancelled")
                }
            },
        )
    }
}
