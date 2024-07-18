package com.record.upload.component.bottomsheet

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.record.designsystem.component.bottomsheet.RecordyBottomSheet
import com.record.designsystem.component.navbar.TopNavigationBar
import com.record.designsystem.theme.Background
import com.record.designsystem.theme.Gray03
import com.record.designsystem.theme.RecordyTheme
import com.record.upload.component.VideoThumbnail
import com.record.upload.extension.GalleryVideo
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectedVideoBottomSheet(
    sheetState: SheetState = rememberModalBottomSheetState(),
    isSheetOpen: Boolean,
    onDismissRequest: () -> Unit,
    galleyVideos: List<GalleryVideo>,
    setVideo: (GalleryVideo) -> Unit,
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    RecordyBottomSheet(
        isSheetOpen = isSheetOpen,
        sheetState = sheetState,
        onDismissRequest = onDismissRequest,
    ) {
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 22.dp),
                    textAlign = TextAlign.Center,
                )
                LazyVerticalGrid(
                    columns = GridCells.Fixed(4),
                    horizontalArrangement = Arrangement.spacedBy(1.dp),
                    verticalArrangement = Arrangement.spacedBy(1.dp),
                ) {
                    items(galleyVideos) { video ->
                        VideoThumbnail(video = video, setVideo = {
                            onDismissRequest()
                            setVideo(video)
                        },)
                    }
                }
            }
        }
    }
}

fun copyFileToTemp(context: Context, sourceUri: Uri, fileName: String): File {
    val tempFile = File(context.cacheDir, fileName)
    context.contentResolver.openInputStream(sourceUri).use { inputStream ->
        tempFile.outputStream().use { outputStream ->
            inputStream?.copyTo(outputStream)
        }
    }
    return tempFile
}
