package com.record.upload.repository

import com.record.upload.model.GalleryVideo
import com.record.upload.model.RecordInfo

interface UploadRepository {
    suspend fun upload(recordInfo: RecordInfo)

    suspend fun getVideosFromGallery(
        page: Int,
        loadSize: Int,
        currentLocation: String?,
    ): Result<List<GalleryVideo>>
}
