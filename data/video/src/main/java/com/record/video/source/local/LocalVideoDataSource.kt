package com.record.video.source.local

import com.record.video.model.local.LocalVideoInfo

interface LocalVideoDataSource {
    suspend fun getVideosFromGallery(
        page: Int,
        loadSize: Int,
        currentLocation: String?,
    ): MutableList<LocalVideoInfo>
}
