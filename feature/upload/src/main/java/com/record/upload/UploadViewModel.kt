package com.record.upload

import android.util.Log
import com.record.ui.base.BaseViewModel
import com.record.upload.extension.GalleryVideo
import com.record.upload.repository.UploadRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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
    suspend fun getPresignedUrl() {
        uploadRepository.getPresignedUrl().onSuccess {
            Log.d("success", "$it")
        }.onFailure {
            Log.d("failure", "${it.message}")
        }
    }
    suspend fun uploadVideoToS3Bucket(file:File){
        uploadRepository.uploadVideoToS3Bucket("",file)
    }
    fun setVideo(video: GalleryVideo) = intent {
        copy(video = video)
    }
    fun onSuccess(path: String?) {
        Log.d("path", "$path")
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
