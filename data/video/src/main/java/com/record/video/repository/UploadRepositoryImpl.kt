package com.record.video.repository

import com.record.model.exception.ApiError
import com.record.upload.model.GalleryVideo
import com.record.upload.model.RecordInfo
import com.record.upload.repository.UploadRepository
import com.record.video.model.local.toDomain
import com.record.video.source.local.LocalVideoDataSource
import com.record.workmanager.upload.UploadEnqueuer
import java.io.IOException
import javax.inject.Inject

class UploadRepositoryImpl @Inject constructor(
    private val uploadEnqueuer: UploadEnqueuer,
    private val localVideoDataSource: LocalVideoDataSource,
) : UploadRepository {
    override suspend fun upload(recordInfo: RecordInfo) {
        uploadEnqueuer.enqueueUploadWork(recordInfo.videoPath, recordInfo.location, recordInfo.keywords, recordInfo.content)
    }

    override suspend fun getVideosFromGallery(page: Int, loadSize: Int, currentLocation: String?): Result<List<GalleryVideo>> = runCatching {
        localVideoDataSource.getVideosFromGallery(page, loadSize, currentLocation)
    }.mapCatching {
        it.map { it.toDomain() }
    }.recoverCatching { exception ->
        when (exception) {
            is IOException -> {
                throw ApiError("IOException")
            }

            else -> {
                throw exception
            }
        }
    }
}
