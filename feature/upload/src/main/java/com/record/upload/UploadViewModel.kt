package com.record.upload

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.record.ui.base.BaseViewModel
import com.record.upload.extension.GalleryVideo
import com.record.upload.extension.reencodeVideo
import com.record.upload.extension.uploadFileToS3PresignedUrl
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

    fun reencodevideo(context: Context, inputFile: File) = viewModelScope.launch {
        val outputFile = File(context.cacheDir, "${inputFile.name}")
        Log.d("outputFile", "$inputFile $outputFile")
        uploadVideoToS3Bucket(inputFile)
        reencodeVideo(inputFile, outputFile) { success, message ->
            if (success) {
                Log.d("outputFile", "$inputFile $outputFile")
                Log.d("outputFileSuccess", "$outputFile")
                uploadVideoToS3Bucket(outputFile)
            } else {
                Log.d("outputFileFail", "$message")
            }
        }
    }
    fun uploadVideoToS3Bucket(file: File) =
        viewModelScope.launch {
            Log.d("outputfile", "$file ")
            Log.d("outputfile", "${uiState.value.bucketUrl} ")
            uploadFileToS3PresignedUrl(uiState.value.bucketUrl, file) { success, message ->
                println(message)
            }
//            uploadRepository.uploadVideoToS3Bucket(
//                uiState.value.bucketUrl,
//                file,
//            ).onSuccess { Log.d("outputsuccess", "$it") }.onFailure {
//                Log.d("outputfailure", "${it.message}")
//            }
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
