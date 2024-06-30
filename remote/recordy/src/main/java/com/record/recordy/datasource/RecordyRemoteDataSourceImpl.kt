package com.record.recordy.datasource

import com.record.network.model.BaseResponse
import com.record.recordy.api.RecordyApi
import com.record.recordy.model.remote.reponse.ResponseGetRecordyDto
import com.record.recordy.model.remote.request.RequestPostRecordyDto
import com.record.recordy.source.remote.RecordyRemoteDataSource
import javax.inject.Inject

class RecordyRemoteDataSourceImpl @Inject constructor(
    private val recordyApi: RecordyApi
) : RecordyRemoteDataSource {
    override suspend fun getRecordy(): BaseResponse<ResponseGetRecordyDto> = recordyApi.getRecordy()

    override suspend fun postRecordy(requestPostRecordyDto: RequestPostRecordyDto): BaseResponse<Unit> =
        recordyApi.postRecordy(requestPostRecordyDto)
}
