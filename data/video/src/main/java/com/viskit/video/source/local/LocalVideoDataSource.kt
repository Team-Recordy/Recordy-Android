package com.viskit.video.source.local

import com.record.video.model.local.LocalImageInfo

interface LocalVideoDataSource {
    suspend fun getVideosFromGallery(
        page: Int,
        loadSize: Int,
        currentLocation: String?,
    ): MutableList<LocalImageInfo>

    suspend fun getImagesFromGallery(
        page: Int,
        loadSize: Int,
        currentLocation: String?,
    ): MutableList<LocalImageInfo>
}
