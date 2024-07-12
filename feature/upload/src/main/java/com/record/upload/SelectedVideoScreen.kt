package com.record.upload

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.record.designsystem.component.button.RecordyButton
import com.record.designsystem.component.navbar.TopNavigationBar
import com.record.designsystem.theme.Background
import com.record.designsystem.theme.Gray03
import com.record.designsystem.theme.RecordyTheme

@Composable
fun SelectedVideoRoute(
    paddingValues: PaddingValues,
    navigateDefinedContent: () -> Unit,
) {
    SelectedVideoScreen(navigateDefinedContent = navigateDefinedContent)
}

@Composable
fun SelectedVideoScreen(
    navigateDefinedContent: () -> Unit,
) { Log.d("images","${getAllVideos(10, null, LocalContext.current)}")
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter),
        ) {
            TopNavigationBar(title = "영상 선택", enableGradation = true)
            Text(
                text = "ⓘ 1080p 이하의 최대 15초 영상을 올려주세요.",
                color = Gray03,
                style = RecordyTheme.typography.caption2,
                maxLines = 1,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
        }
        RecordyButton(
            modifier = Modifier.align(Alignment.BottomCenter),
            text = "다음",
            enabled = true,
            onClick = {},
        )
    }

}
@Preview
@Composable
fun SelectedVideoScreenPreview() {
    RecordyTheme {
        VideoPickerScreen(navigateSelectedVideo = { /*TODO*/ })
    }
}

fun getAllVideos(
    loadSize: Int,
    currentLocation: String?,
    context: Context
): MutableList<GalleryVideo> {
    val galleryVideoList = mutableListOf<GalleryVideo>()
    // 외장 메모리에 있는 비디오 파일의 URI를 받도록 함
    val uriExternal: Uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
    // 커서에 가져올 정보에 대해서 지정한다.
    val projection = arrayOf(
        MediaStore.Video.VideoColumns.DATA,
        MediaStore.Video.VideoColumns.DISPLAY_NAME, // 이름
        MediaStore.Video.VideoColumns.SIZE, // 크기
        MediaStore.Video.VideoColumns.DATE_TAKEN,
        MediaStore.Video.VideoColumns.DATE_ADDED, // 추가된 날짜
        MediaStore.Video.VideoColumns._ID
    )
    val resolver = context.contentResolver

    var selection: String? = null
    var selectionArgs: Array<String>? = null

    val query = resolver.query(
        uriExternal,
        projection,
        selection,
        selectionArgs,
        "${MediaStore.Video.VideoColumns.DATE_ADDED} DESC"
    )

    query?.use { cursor ->
        Log.d("gallery cursor", "Cursor count: ${cursor.count}.")

        if (cursor.moveToFirst()) {
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.VideoColumns._ID)
            val nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.DISPLAY_NAME)
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
                        size = size
                    )
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
    val size: Int
)