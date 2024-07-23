package com.record.upload

import androidx.lifecycle.viewModelScope
import com.record.common.util.encodingString
import com.record.designsystem.component.snackbar.SnackBarType
import com.record.keyword.repository.KeywordRepository
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
        copy(showShouldShowRationaleDialog = true)
    }

    fun hideShouldShowRationaleDialog() = intent {
        copy(showShouldShowRationaleDialog = false)
    }

    fun showExitUploadDialog() = intent {
        copy(showExitUploadDialog = true)
    }

    fun hideExitUploadDialog() = intent {
        copy(showExitUploadDialog = false)
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
