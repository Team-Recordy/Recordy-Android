package com.record.recordy.api

import com.record.network.model.BaseResponse
import com.record.recordy.model.remote.reponse.ResponseGetRecordyDto
import com.record.recordy.model.remote.request.RequestPostRecordyDto

interface RecordyApi {
    suspend fun getRecordy(): BaseResponse<ResponseGetRecordyDto>
    suspend fun postRecordy(requestPostRecordyDto: RequestPostRecordyDto): BaseResponse<Unit>
}
