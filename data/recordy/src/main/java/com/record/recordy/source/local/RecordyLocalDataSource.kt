package com.record.recordy.source.local

import kotlinx.coroutines.flow.Flow
import org.sopt.datastore.RecordyLocalData

interface RecordyLocalDataSource {
    val recordyLocalData: Flow<RecordyLocalData>
    suspend fun setRecordyLocalData(recordyData: RecordyLocalData)
}
