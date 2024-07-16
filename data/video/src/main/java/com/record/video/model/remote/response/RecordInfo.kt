package com.record.video.model.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecordInfo(
    @SerialName("bookmarkCount")
    val bookmarkCount: Int,
    @SerialName("content")
    val content: String,
    @SerialName("fileUrl")
    val fileUrl: FileUrl,
    @SerialName("id")
    val id: Int,
    @SerialName("location")
    val location: String,
    @SerialName("uploaderId")
    val uploaderId: Int,
    @SerialName("uploaderNickname")
    val uploaderNickname: String,
    @SerialName("isMine")
    val isMine: Boolean,
)

@Serializable
data class FileUrl(
    @SerialName("thumbnailUrl")
    val thumbnailUrl: String,
    @SerialName("videoUrl")
    val videoUrl: String,
)
