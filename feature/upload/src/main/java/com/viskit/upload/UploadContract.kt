package com.viskit.upload

import com.viskit.designsystem.component.snackbar.SnackBarType
import com.viskit.model.AlertInfo
import com.viskit.ui.base.SideEffect
import com.viskit.ui.base.UiState
import com.viskit.upload.model.GalleryImage
import com.viskit.upload.navigation.UploadRoute
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

data class UploadState(
    val contentList: List<String> = persistentListOf(),
    val galleryList: ImmutableList<GalleryImage> = emptyList<GalleryImage>().toImmutableList(),
    val galleryPage: Int = 1,
    val isItemLoading: Boolean = false,
    val video: GalleryImage? = null,
    val alertInfo: AlertInfo = AlertInfo(),
    val isSystemAlert: Boolean = false,
    val isSelectedVideoSheetOpen: Boolean = false,
    val isSelectedDefinedContentSheetOpen: Boolean = false,
    val buttonEnabled: Boolean = false,
    val bucketUrl: String = "",
    val thumbnailUrl: String = "",
    val locationTextValue: String = "",
    val contentTextValue: String = "",
    val selectPlace: UploadRoute.Upload = UploadRoute.Upload(),
) : UiState

sealed interface UploadSideEffect : SideEffect {
    data object PopBackStack : UploadSideEffect
    data class ShowSnackBar(val msg: String, val type: SnackBarType) : UploadSideEffect
    data object FocusLocation : UploadSideEffect
    data object FocusContent : UploadSideEffect
    data object NavigateToSearchPlace : UploadSideEffect
}
