package com.record.upload

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.record.common.util.encodingString
import com.record.designsystem.component.snackbar.SnackBarType
import com.record.keyword.repository.KeywordRepository
import com.record.ui.base.BaseViewModel
import com.record.upload.extension.GalleryVideo
import com.record.upload.model.VideoInfo
import com.record.upload.repository.UploadRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
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

    suspend fun getPresignedUrl() = viewModelScope.launch {
        uploadRepository.getPresignedUrl().onSuccess {
            bucket(it.videoUrl, it.imageUrl)
        }.onFailure {
            Log.d("failure", "${it.message}")
        }
    }

    fun uploadVideoToS3Bucket(file: File) = viewModelScope.launch(Dispatchers.IO) {
        uploadRepository.uploadVideoToS3Bucket(
            uiState.value.bucketUrl,
            file,
        ).onSuccess { videoUrl ->
            uploadRepository.uploadThumbnailToS3Bucket(
                uiState.value.thumbnailUrl,
                file,
            ).onSuccess { thumbNailUrl ->
                uploadRecord(videoUrl, thumbNailUrl)
            }
        }.onFailure {
        }
    }

    fun uploadRecord(videoS3Url: String, thumbnailS3Url: String) {
        viewModelScope.launch {
            uploadRepository.uploadRecord(
                videoInfo = VideoInfo(
                    location = uiState.value.locationTextValue,
                    content = uiState.value.contentTextValue,
                    keywords = encodingString(uiState.value.selectedList.joinToString(separator = ",")).trim(),
                    videoUrl = videoS3Url,
                    previewUrl = thumbnailS3Url,
                ),
            ).onSuccess {
                Log.d("testUpload", "upload")
            }.onFailure {
            }
            popBackStack()
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
