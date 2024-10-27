package com.record.upload

import com.record.designsystem.component.snackbar.SnackBarType
import com.record.model.AlertInfo
import com.record.ui.base.SideEffect
import com.record.ui.base.UiState
import com.record.upload.model.GalleryVideo
import com.record.upload.navigation.UploadRoute
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

data class UploadState(
    val contentList: List<String> = persistentListOf(),
    val galleryList: ImmutableList<GalleryVideo> = emptyList<GalleryVideo>().toImmutableList(),
    val galleryPage: Int = 1,
    val isItemLoading: Boolean = false,
    val video: GalleryVideo? = null,
    val alertInfo: AlertInfo = AlertInfo(),
    val isSelectedVideoSheetOpen: Boolean = false,
    val isSelectedDefinedContentSheetOpen: Boolean = false,
    val buttonEnabled: Boolean = false,
    val bucketUrl: String = "",
    val thumbnailUrl: String = "",
    val locationTextValue: String = "",
    val contentTextValue: String = "",
    val selectPlace:UploadRoute.Upload=UploadRoute.Upload()
) : UiState

sealed interface UploadSideEffect : SideEffect {
    data object PopBackStack : UploadSideEffect
    data class ShowSnackBar(val msg: String, val type: SnackBarType) : UploadSideEffect
    data object FocusLocation : UploadSideEffect
    data object FocusContent : UploadSideEffect
    data object NavigateToSearchPlace : UploadSideEffect
}
