package com.record.upload

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.VideoFrameDecoder

@Composable
fun SelectedVideoRoute(
    paddingValues: PaddingValues,
) {
    SelectedVideoScreen()
}

@Composable
fun SelectedVideoScreen(
) {
    Log.d("images", "${getAllVideos(10, null, LocalContext.current)}")
    val a = getAllVideos(10, null, LocalContext.current)
}

@Composable
fun VideoThumbnail(video: GalleryVideo) {
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
            .padding(8.dp)
            .size(85.dp),
    ) {
        Image(
            painter = painter,
            contentDescription = "Video Thumbnail",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(RectangleShape),
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
                Log.d("gallery contentUri", "$contentUri")
                galleryVideoList.add(
                    GalleryVideo(
                        id,
                        filepath = filepath,
                        uri = contentUri,
                        name = name,
                        date = date ?: "",
                        size = size,
                    ),
                )
            } while (cursor.moveToNext())
        }
    }

    return galleryVideoList
}

data class GalleryVideo(
    val id: Long,
    val filepath: String,
    val uri: Uri,
    val name: String,
    val date: String,
    val size: Int,
)
