package com.record.upload

import androidx.lifecycle.viewModelScope
import com.record.common.util.encodingString
import com.record.designsystem.component.snackbar.SnackBarType
import com.record.keyword.repository.KeywordRepository
import com.record.model.AlertInfo
import com.record.ui.base.BaseViewModel
import com.record.upload.model.GalleryVideo
import com.record.upload.model.RecordInfo
import com.record.upload.repository.UploadRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UploadViewModel @Inject constructor(
    private val uploadRepository: UploadRepository,
    private val keywordRepository: KeywordRepository,
) : BaseViewModel<UploadState, UploadSideEffect>(UploadState()) {
    fun getKeyWordList() = viewModelScope.launch {
        keywordRepository.getKeywords().onSuccess {
            intent { copy(contentList = it.keywords) }
        }
    }

    fun setSelectedList(selectedContent: List<String>) = intent {
        copy(selectedList = selectedContent)
    }

    fun upload() = viewModelScope.launch {
        val filePath = uiState.value.video?.filepath ?: return@launch

        val recordInfo = RecordInfo(
            filePath,
            uiState.value.locationTextValue,
            uiState.value.contentTextValue,
            keywords = encodingString(uiState.value.selectedList.joinToString(separator = ",")).trim(),
        )
        uploadRepository.upload(recordInfo)
        popBackStack()
    }

    fun onLoadMore() = viewModelScope.launch(Dispatchers.IO) {
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

    fun updateLocationTextField(locationValue: String) = intent {
        copy(locationTextValue = locationValue)
    }

    fun updateContentTextField(contentValue: String) = intent {
        copy(contentTextValue = contentValue)
    }

    fun setVideo(video: GalleryVideo) = intent {
        copy(video = video)
    }

    fun bucket(video: String, thumbnail: String) = intent {
        copy(bucketUrl = video, thumbnailUrl = thumbnail)
    }

    fun showShouldShowRationaleDialog() = intent {
        copy(
            alertInfo = AlertInfo(
                showDialog = true,
                title = "필수 권한을 허용해주세요",
                subTitle = "프로필 사진 업로드를 위해 \n사진 라이브러리에 접근하도록 허용해 주세요.",
                negativeButtonLabel = "닫기",
                positiveButtonLabel = "지금 설정",
            ),
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

    fun makeSnackBar() = viewModelScope.launch {
        postSideEffect(UploadSideEffect.ShowSnackBar("기준에 맞는 영상을 선택해 주세요.", SnackBarType.WARNING))
    }
}
