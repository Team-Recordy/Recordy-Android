package com.record.upload

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.record.ui.base.BaseViewModel
import com.record.upload.extension.GalleryVideo
import com.record.upload.extension.uploadFileToS3PresignedUrl
import com.record.upload.extension.uploadFileToS3ThumbnailPresignedUrl
import com.record.upload.repository.UploadRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class UploadViewModel @Inject constructor(
    private val uploadRepository: UploadRepository,
) :
    BaseViewModel<UploadState, UploadSideEffect>(UploadState()) {
    fun setSelectedList(selectedContent: String) = intent {
        val newSelectedList = selectedList.toMutableList()
        if (newSelectedList.contains(selectedContent)) {
            newSelectedList.remove(selectedContent)
        } else {
            if (newSelectedList.size < 3) newSelectedList.add(selectedContent)
        }
        copy(selectedList = newSelectedList)
    }

    suspend fun getPresignedUrl() = viewModelScope.launch {
        uploadRepository.getPresignedUrl().onSuccess {
            Log.d("success", "$it")
            bucket(it.videoUrl, it.imageUrl)
        }.onFailure {
            Log.d("failure", "${it.message}")
        }
    }

    fun uploadVideoToS3Bucket(context: Context, file: File) =
        viewModelScope.launch {
            uploadFileToS3PresignedUrl(uiState.value.bucketUrl, file) { success, message ->
                println(message)
                Log.d("messageFile", "$message ")
            }
            uploadFileToS3ThumbnailPresignedUrl(context, uiState.value.thumbnailUrl, file) { success, message ->
                println(message)
                Log.d("messageThumbnail", "$message ")
            }
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
}
