package com.record.recordy.source.remote

import com.record.network.model.BaseResponse
import com.record.recordy.model.remote.reponse.ResponseGetRecordyDto
import com.record.recordy.model.remote.request.RequestPostRecordyDto

interface RecordyRemoteDataSource {
    suspend fun getRecordy(): BaseResponse<ResponseGetRecordyDto>
    suspend fun postRecordy(requestPostRecordyDto: RequestPostRecordyDto): BaseResponse<Unit>
}
