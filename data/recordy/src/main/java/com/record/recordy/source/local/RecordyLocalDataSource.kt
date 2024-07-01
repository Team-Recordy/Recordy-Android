package com.record.recordy.source.local

import com.record.datastore.RecordyLocalData
import kotlinx.coroutines.flow.Flow

interface RecordyLocalDataSource {
    val recordyLocalData: Flow<RecordyLocalData>
    suspend fun setRecordyLocalData(recordyData: RecordyLocalData)
}
