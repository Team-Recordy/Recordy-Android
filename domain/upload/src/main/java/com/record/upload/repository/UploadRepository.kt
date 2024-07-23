package com.record.upload.repository

import com.record.upload.model.RecordInfo

interface UploadRepository {
    suspend fun upload(recordInfo: RecordInfo)
}
