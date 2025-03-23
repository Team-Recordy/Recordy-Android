package com.viskit.navigator

import com.viskit.designsystem.component.snackbar.SnackBarType
import com.viskit.ui.base.SideEffect
import com.viskit.ui.base.UiState

data class MainState(
    val snackBarVisible: Boolean = false,
    val snackBarType: SnackBarType = SnackBarType.CHECK,
    val snackBarMessage: String = "",
    val snackBarBottomPadding: Int = 20,
    val uploadProgress: Int = 0,
    val isUploading: Boolean = false,
) : UiState

sealed interface MainSideEffect : SideEffect
