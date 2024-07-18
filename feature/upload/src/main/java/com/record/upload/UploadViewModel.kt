package com.record.upload

import android.content.Context
import android.util.Base64
import android.util.Log
import androidx.lifecycle.viewModelScope
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
import java.net.URLDecoder
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

    private fun decodeBase64(encodedString: String): String? {
        val decodedBytes = Base64.decode(encodedString, Base64.DEFAULT)
        val decodedString = String(decodedBytes, Charsets.UTF_8)
        return URLDecoder.decode(decodedString, "UTF-8")
    }

    fun uploadVideoToS3Bucket(context: Context, file: File) =
        viewModelScope.launch {
            var a = ""
            var b = ""
            uploadFileToS3PresignedUrl(uiState.value.bucketUrl, uiState.value.thumbnailUrl, file) { success, message ->
                println(message)
                a = removeQueryParameters(message)
                Log.d("messageFile", "$message")
                if (success) {
                    uploadFileToS3ThumbnailPresignedUrl(
                        context,
                        uiState.value.thumbnailUrl,
                        file,
                    ) { success, message ->
                        println(message)
                        b = removeQueryParameters(message)
                        Log.d("messageThumbnail", "$message ")
                        uploadRecord(a, b)
                    }
                }
            }
        }

    fun uploadRecord(a: String, b: String) =
        viewModelScope.launch {
            uploadRepository.uploadRecord(
                videoInfo = VideoInfo(
                    location = "test",
                    content = "test",
                    keywords = encodingString("감각적인,강렬한,귀여운").trim(),
                    videoUrl = a,
                    previewUrl = b,
                ),
            )
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
