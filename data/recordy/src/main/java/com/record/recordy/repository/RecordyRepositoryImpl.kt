package com.record.recordy.repository

import com.record.model.CoreData
import com.record.model.exception.ApiError
import com.record.recordy.model.Recordy
import com.record.recordy.model.remote.reponse.toDomain
import com.record.recordy.model.remote.request.toData
import com.record.recordy.source.local.RecordyLocalDataSource
import com.record.recordy.source.remote.RecordyRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import javax.inject.Inject

class RecordyRepositoryImpl @Inject constructor(
    private val recordyLocalDataSource: RecordyLocalDataSource,
    private val recordyRemoteDataSource: RecordyRemoteDataSource,
) : RecordyRepository {
    override suspend fun getRecordy(): Result<Recordy> =
        runCatching { recordyRemoteDataSource.getRecordy().data!! }
            .mapCatching { it.toDomain() }
            .recoverCatching { exception ->
                when (exception) {
                    is HttpException -> {
                        when (exception.code()) {
                            in 300..399 -> throw ApiError("엥")
                            400 -> throw ApiError("무슨무슨 오류")
                            401 -> throw ApiError("토큰 오류")
                            402 -> throw ApiError("무슨무슨 오류")
                            403 -> throw ApiError("무슨무슨 오류")
                            404 -> throw ApiError("무슨무슨 오류")
                            else -> throw ApiError("서버오류")
                        }
                    }
                    else -> throw exception
                }
            }

    override suspend fun postRecordy(recordy: Recordy): Result<Unit> =
        runCatching { recordyRemoteDataSource.postRecordy(recordy.toData()).data!! }
            .recoverCatching { exception ->
                when (exception) {
                    is HttpException -> {
                        when (exception.code()) {
                            in 300..399 -> throw ApiError("엥")
                            400 -> throw ApiError("무슨무슨 오류")
                            401 -> throw ApiError("토큰 오류")
                            402 -> throw ApiError("무슨무슨 오류")
                            403 -> throw ApiError("무슨무슨 오류")
                            404 -> throw ApiError("무슨무슨 오류")
                            else -> throw ApiError("서버오류")
                        }
                    }
                    else -> throw exception
                }
            }

    override fun getData(): Flow<CoreData> = recordyLocalDataSource.recordyLocalData.map { it ->
        CoreData(it.a, it.b)
    }
}
