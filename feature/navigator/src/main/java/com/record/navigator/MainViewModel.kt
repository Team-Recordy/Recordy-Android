package com.record.navigator

import androidx.lifecycle.viewModelScope
import com.record.designsystem.component.snackbar.SnackBarType
import com.record.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(): BaseViewModel<MainState,MainSideEffect>(MainState()) {
    fun onShowSnackbar(msg: String, type: SnackBarType) = viewModelScope.launch {
        intent { copy(snackBarMessage = msg, snackBarType = type, snackBarVisible = true) }
        delay(3000L)
        intent { copy(snackBarVisible = false) }
    }
}
