package com.record.upload

import android.content.ContentUris
import android.content.Context
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.VideoFrameDecoder
import com.abedelazizshe.lightcompressorlibrary.CompressionListener
import com.abedelazizshe.lightcompressorlibrary.VideoCompressor
import com.abedelazizshe.lightcompressorlibrary.VideoQuality
import com.abedelazizshe.lightcompressorlibrary.config.Configuration
import com.abedelazizshe.lightcompressorlibrary.config.SaveLocation
import com.abedelazizshe.lightcompressorlibrary.config.SharedStorageConfiguration
import com.record.designsystem.theme.White
import com.record.ui.extension.customClickable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun SelectedVideoRoute(
    paddingValues: PaddingValues,
) {
    SelectedVideoScreen()
}

@Composable
fun SelectedVideoScreen() {
    val a = getAllVideos(10, null, LocalContext.current)
}

@Composable
fun VideoThumbnail(
    video: GalleryVideo,
    setVideo: (GalleryVideo) -> Unit,
) {
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components {
            add(VideoFrameDecoder.Factory())
        }
        .crossfade(true)
        .build()

    val painter = rememberAsyncImagePainter(
        model = video.filepath,
        imageLoader = imageLoader,
    )
    Box(
        modifier = Modifier
            .width(100.dp)
            .height(100.dp),
    ) {
        Image(
            painter = painter,
            contentDescription = "Video Thumbnail",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(RectangleShape)
                .customClickable { setVideo(video) },
        )
        Text(
            text = formatDuration(video.duration),
            fontSize = 12.sp,
            color = White,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 4.dp, end = 6.dp),
            textAlign = TextAlign.End,
        )
    }
}

fun getAllVideos(
    loadSize: Int,
    currentLocation: String?,
    context: Context,
): MutableList<GalleryVideo> {
    val galleryVideoList = mutableListOf<GalleryVideo>()
    val uriExternal: Uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
    val projection = arrayOf(
        MediaStore.Video.VideoColumns.DATA,
        MediaStore.Video.VideoColumns.DISPLAY_NAME, // 이름
        MediaStore.Video.VideoColumns.SIZE, // 크기
        MediaStore.Video.VideoColumns.DATE_TAKEN,
        MediaStore.Video.VideoColumns.DATE_ADDED, // 추가된 날짜
        MediaStore.Video.VideoColumns._ID,
    )
    val resolver = context.contentResolver

    var selection: String? = null
    var selectionArgs: Array<String>? = null

    val query = resolver.query(
        uriExternal,
        projection,
        selection,
        selectionArgs,
        "${MediaStore.Video.VideoColumns.DATE_ADDED} DESC",
    )

    query?.use { cursor ->
        if (cursor.moveToFirst()) {
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.VideoColumns._ID)
            val nameColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.DISPLAY_NAME)
            val filePathColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.DATA)
            val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.SIZE)
            val dateColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.DATE_TAKEN)

            do {
                val id = cursor.getLong(idColumn)
                val filepath = cursor.getString(filePathColumn)
                val name = cursor.getString(nameColumn)
                val size = cursor.getInt(sizeColumn)
                val date = cursor.getString(dateColumn)
                val contentUri = ContentUris.withAppendedId(uriExternal, id)
                val duration = getVideoDuration(context, contentUri)
                galleryVideoList.add(
                    GalleryVideo(
                        id,
                        filepath = filepath,
                        uri = contentUri,
                        name = name,
                        date = date ?: "",
                        size = size,
                        duration = duration,
                    ),
                )
            } while (cursor.moveToNext())
        }
    }

    return galleryVideoList
}

fun getVideoDuration(context: Context, uri: Uri): Long {
    val retriever = MediaMetadataRetriever()
    return try {
        retriever.setDataSource(context, uri)
        val time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
        val timeInMillisec = time?.toLongOrNull() ?: 0L
        timeInMillisec
    } catch (e: Exception) {
        e.printStackTrace()
        0L
    } finally {
        retriever.release()
    }
}
data class GalleryVideo(
    val id: Long,
    val filepath: String,
    val uri: Uri,
    val name: String,
    val date: String,
    val size: Int,
    val duration: Long,
)

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

fun formatDuration(durationMillis: Long): String {
    val minutes = (durationMillis / 1000) / 60
    val seconds = (durationMillis / 1000) % 60
    return String.format("%d:%02d", minutes, seconds)
}
