package com.record.recordy.repository

import com.record.model.CoreData
import com.record.recordy.model.Recordy
import kotlinx.coroutines.flow.Flow

interface RecordyRepository {
    suspend fun getRecordy(): Result<Recordy>
    suspend fun postRecordy(recordy: Recordy): Result<Unit>
    fun getData(): Flow<CoreData>
}
