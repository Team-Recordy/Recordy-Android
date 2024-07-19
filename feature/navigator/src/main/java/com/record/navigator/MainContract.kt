package com.record.navigator

import com.record.designsystem.component.snackbar.SnackBarType
import com.record.ui.base.SideEffect
import com.record.ui.base.UiState

data class MainState(
    val snackBarVisible: Boolean = false,
    val snackBarType: SnackBarType = SnackBarType.CHECK,
    val snackBarMessage: String = "",
    val snackBarBottomPadding: Int = 20,
) : UiState

sealed interface MainSideEffect : SideEffect
