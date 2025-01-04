package com.record.video.model

data class VideoData(
    val bookmarkId: Long,
    val id: Long,
    val isBookmark: Boolean,
    val bookmarkCount: Int,
    val content: String,
    val videoUrl: String,
    val previewUrl: String,
    val placeId: Long,
    val location: String,
    val exhibitionName: String,
    val uploaderId: Long,
    val nickname: String,
    val isMine: Boolean,
)

fun VideoData.toCore() = com.record.model.VideoData(
    bookmarkCount = this.bookmarkCount,
    id = this.id,
    isBookmark = this.isBookmark,
    bookmarkId = this.bookmarkId,
    content = this.content,
    videoUrl = this.videoUrl,
    previewUrl = this.previewUrl,
    placeId = this.placeId,
    location = this.location,
    exhibitionName = this.exhibitionName,
    uploaderId = this.uploaderId,
    nickname = this.nickname,
    isMine = this.isMine,
)
