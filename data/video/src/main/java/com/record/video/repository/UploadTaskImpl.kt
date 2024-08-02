package com.record.video.repository

import com.record.common.intentprovider.UploadBroadCaster
import com.record.common.util.ProgressListener
import com.record.upload.model.VideoInfo
import com.record.video.model.remote.request.toData
import com.record.video.source.remote.RemoteUploadDataSource
import com.record.workmanager.upload.UploadTask
import java.io.File
import javax.inject.Inject

class UploadTaskImpl @Inject constructor(
    private val remoteUploadDataSource: RemoteUploadDataSource,
    private val uploadBroadCaster: UploadBroadCaster,
) : UploadTask {
    override suspend fun upload(videoPath: String, location: String, content: String, keywords: String): Result<Unit> {
        val result = runCatching {
            uploadBroadCaster.sendUploadStart()
            val urls = remoteUploadDataSource.getUploadUrl()
            val videoUrl = urls.videoUrl
            val previewUrl = urls.thumbnailUrl
            remoteUploadDataSource.uploadVideoToS3Bucket(
                videoUrl,
                File(videoPath),
                listener = object : ProgressListener {
                    override fun onProgressUpdate(percentage: Int) {
                        uploadBroadCaster.sendUploadProgress(percentage)
                    }
                },
            ).let { videoUri ->
                remoteUploadDataSource.uploadThumbnailToS3Bucket(previewUrl, File(videoPath)).let { previewUri ->
                    remoteUploadDataSource.uploadRecord(
                        VideoInfo(
                            location = location,
                            content = content,
                            keywords = keywords,
                            videoUrl = videoUri,
                            previewUrl = previewUri,
                        ).toData(),
                    )
                }
            }
        }

        result.onSuccess {
            uploadBroadCaster.sendUploadSuccess()
        }.onFailure {
            uploadBroadCaster.sendUploadFailure()
        }

        return result
    }
}
