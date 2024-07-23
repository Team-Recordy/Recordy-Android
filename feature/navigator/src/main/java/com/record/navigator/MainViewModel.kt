package com.record.navigator

import androidx.lifecycle.viewModelScope
import com.record.designsystem.component.snackbar.SnackBarType
import com.record.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel<MainState, MainSideEffect>(MainState()) {
    fun onShowSnackbar(msg: String, type: SnackBarType) = viewModelScope.launch {
        intent { copy(snackBarMessage = msg, snackBarType = type, snackBarVisible = true) }
        delay(3000L)
        intent { copy(snackBarVisible = false) }
    }

    fun clickSnackbar() = viewModelScope.launch {
        intent { copy(snackBarVisible = false) }
    }

    fun dismissSnackbar() = intent { copy(snackBarVisible = false) }
    fun updateProgress(percentage: Int) = viewModelScope.launch {
        intent {
            copy(uploadProgress = percentage)
        }
    }
    fun startUpload() = viewModelScope.launch {
        intent {
            copy(isUploading = true)
        }
    }
    fun stopUpload() = viewModelScope.launch {
        intent {
            copy(isUploading = false)
        }
    }
    fun successUpload() = viewModelScope.launch {
        intent {
            copy(isUploading = false)
        }
        onShowSnackbar("업로드를 성공했습니다.", SnackBarType.CHECK)
    }

    fun failUpload() = viewModelScope.launch {
        intent {
            copy(isUploading = false)
        }
        onShowSnackbar("업로드를 실패했습니다.", SnackBarType.WARNING)
    }
}
