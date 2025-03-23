package com.viskit.setting.profileedit

import com.record.model.AlertInfo
import com.record.model.ValidateResult
import com.record.ui.base.SideEffect
import com.record.ui.base.UiState
import com.record.upload.model.GalleryImage
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class ProfileEditState(
    val username: String = "",
    val galleryList: ImmutableList<GalleryImage> = emptyList<GalleryImage>().toImmutableList(),
    val galleryPage: Int = 1,
    val isItemLoading: Boolean = false,
    val image: GalleryImage? = null,
    val defaultProfileImgUrl: String? = null,
    val isSelected: Boolean = false,
    val alertInfo: AlertInfo = AlertInfo(),
    val isSelectedImageSheetOpen: Boolean = false,
    val nicknameValidate: ValidateResult = ValidateResult.Inputting,
    val placeHolder: String = "",
    val btnEnable: Boolean = false,
) : UiState

sealed interface ProfileEditSideEffect : SideEffect {
    data object BackToSetting : ProfileEditSideEffect
}
