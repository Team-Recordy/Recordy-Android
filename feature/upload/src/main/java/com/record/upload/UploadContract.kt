package com.record.upload

import com.record.ui.base.SideEffect
import com.record.ui.base.UiState
import com.record.upload.extension.GalleryVideo
import kotlinx.collections.immutable.persistentListOf

data class UploadState(
    val contentList: List<String> = persistentListOf(),
    val selectedList: List<String> = persistentListOf(),
    val video: GalleryVideo? = null,
    val showShouldShowRationaleDialog: Boolean = false,
    val showExitUploadDialog: Boolean = false,
    val isSelectedVideoSheetOpen: Boolean = false,
    val isSelectedDefinedContentSheetOpen: Boolean = false,
    val bucketUrl: String = "",
    val thumbnailUrl: String = "",
) : UiState

sealed interface UploadSideEffect : SideEffect {
    data object PopBackStack : UploadSideEffect
}
