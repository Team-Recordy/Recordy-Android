package com.record.upload

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.record.designsystem.component.snackbar.SnackBarType
import com.record.keyword.repository.KeywordRepository
import com.record.model.AlertInfo
import com.record.ui.base.BaseViewModel
import com.record.upload.model.GalleryImage
import com.record.upload.model.RecordInfo
import com.record.upload.navigation.UploadRoute
import com.record.upload.repository.UploadRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

@HiltViewModel
class UploadViewModel @Inject constructor(
    private val uploadRepository: UploadRepository,
    private val keywordRepository: KeywordRepository,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<UploadState, UploadSideEffect>(UploadState()) {

    private val mutex = Mutex()
    fun getKeyWordList() = viewModelScope.launch {
        keywordRepository.getKeywords().onSuccess {
            intent { copy(contentList = it.keywords) }
        }
    }
    fun setSelectedPlace(id: String, name: String, address: String) {
        if (uiState.value.selectPlace.name != name) updateLocationTextField("")

        if (id.isNotEmpty() && name.isNotEmpty() && address.isNotEmpty()) {
            intent {
                copy(
                    selectPlace = UploadRoute.Upload(
                        id = id.toLong(),
                        name = name,
                        address = address,
                    ),
                )
            }
        }
    }

    fun upload() = viewModelScope.launch {
        val filePath = uiState.value.video?.filepath ?: return@launch

        val recordInfo = RecordInfo(
            filePath,
            uiState.value.contentTextValue,
            placeId = uiState.value.selectPlace.id,
            exhibitionName = uiState.value.locationTextValue,
        )
        uploadRepository.upload(recordInfo)
        popBackStack()
    }

    fun onLoadMore() = viewModelScope.launch(Dispatchers.IO) {
        mutex.withLock {
            val list = uiState.value.galleryList
            intent {
                copy(isItemLoading = true)
            }
            uploadRepository.getVideosFromGallery(uiState.value.galleryPage, 20, null).onSuccess {
                intent {
                    copy(galleryList = (list + it).toImmutableList(), galleryPage = uiState.value.galleryPage + 1, isItemLoading = false)
                }
            }.onFailure {
                intent {
                    copy(isItemLoading = false)
                }
            }
        }
    }

    fun updateLocationTextField(locationValue: String) = intent {
        copy(locationTextValue = locationValue)
    }

    fun updateContentTextField(contentValue: String) = intent {
        copy(contentTextValue = contentValue)
    }

    fun setVideo(video: GalleryImage) = intent {
        copy(video = video)
    }

    fun showShouldShowRationaleDialog() = intent {
        copy(
            alertInfo = AlertInfo(
                showDialog = true,
                title = "필수 권한을 허용해주세요",
                subTitle = "영상 업로드를 위해 \n사진 라이브러리에 접근을 항상 허용해 주세요.",
                negativeButtonLabel = "닫기",
                positiveButtonLabel = "지금 설정",
            ),
            isSystemAlert = true,
        )
    }

    fun hideUploadDialog() = intent {
        copy(
            alertInfo = AlertInfo(
                showDialog = false,
            ),
        )
    }

    fun showExitUploadDialog() = intent {
        copy(
            alertInfo = AlertInfo(
                showDialog = true,
                title = "화면을 나가시겠어요?",
                subTitle = "지금까지 작성하신 내용이 모두 사라져요.",
                negativeButtonLabel = "취소",
                positiveButtonLabel = "나가기",
            ),
            isSystemAlert = false,
        )
    }

    fun showIsSelectedVideoSheetOpen() = intent {
        copy(isSelectedVideoSheetOpen = true)
    }

    fun hideIsSelectedVideoSheetOpen() = intent {
        copy(isSelectedVideoSheetOpen = false)
    }

    fun showIsSelectedDefinedContentSheetOpen() = intent {
        copy(isSelectedDefinedContentSheetOpen = true)
    }

    fun hideIsSelectedDefinedContentSheetOpen() = intent {
        copy(isSelectedDefinedContentSheetOpen = false)
    }

    fun popBackStack() {
        postSideEffect(UploadSideEffect.PopBackStack)
    }
    fun navigateToSearchPlace() {
        postSideEffect(UploadSideEffect.NavigateToSearchPlace)
    }

    fun makeSnackBar() = viewModelScope.launch {
        postSideEffect(UploadSideEffect.ShowSnackBar("기준에 맞는 영상을 선택해 주세요.", SnackBarType.WARNING))
    }
}
