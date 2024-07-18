package com.record.upload

import android.content.Context
import android.util.Base64
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.record.keyword.repository.KeywordRepository
import com.record.ui.base.BaseViewModel
import com.record.upload.extension.GalleryVideo
import com.record.upload.extension.uploadFileToS3PresignedUrl
import com.record.upload.extension.uploadFileToS3ThumbnailPresignedUrl
import com.record.upload.model.VideoInfo
import com.record.upload.repository.UploadRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import java.net.URL
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
            bucket(it.videoUrl, it.imageUrl)
        }.onFailure {
            Log.d("failure", "${it.message}")
        }
    }

    fun uploadVideoToS3Bucket(context: Context, file: File) =
        viewModelScope.launch {
            var a = ""
            var b = ""
            uploadFileToS3PresignedUrl(
                uiState.value.bucketUrl,
                uiState.value.thumbnailUrl,
                file,
            ) { success, message ->
                println(message)
                a = removeQueryParameters(message)
                if (success) {
                    uploadFileToS3ThumbnailPresignedUrl(
                        context,
                        uiState.value.thumbnailUrl,
                        file,
                    ) { success, message ->
                        println(message)
                        b = removeQueryParameters(message)
                        uploadRecord(a, b)
                    }
                }
            }
        }

    fun uploadRecord(a: String, b: String) =
        viewModelScope.launch {
            uploadRepository.uploadRecord(
                videoInfo = VideoInfo(
                    location = uiState.value.locationTextValue,
                    content = uiState.value.contentTextValue,
                    keywords = encodingString(uiState.value.selectedList.joinToString(separator = ",")).trim(),
                    videoUrl = a,
                    previewUrl = b,
                ),
            ).onSuccess {
                popBackStack()
            }
        }

    fun updateLocationTextField(locationValue: String) = intent {
        copy(locationTextValue = locationValue)
    }

    fun updateContentTextField(contentValue: String) = intent {
        copy(contentTextValue = contentValue)
    }

    private fun encodingString(contentValue: String): String {
        val bytes = contentValue.toByteArray(Charsets.UTF_8)
        val encodedString = Base64.encodeToString(bytes, Base64.DEFAULT)
        return encodedString
    }

    fun removeQueryParameters(urlString: String): String {
        val url = URL(urlString)
        val cleanUrl = URL(url.protocol, url.host, url.port, url.path)
        return cleanUrl.toString()
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
}
