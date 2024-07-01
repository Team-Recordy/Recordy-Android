package com.record.recordy.datasource

import android.util.Log
import androidx.datastore.core.DataStore
import com.record.datastore.RecordyLocalData
import com.record.recordy.source.local.RecordyLocalDataSource
import java.io.IOException
import javax.inject.Inject

class RecordyLocalDataSourceImpl @Inject constructor(
    private val userPreferences: DataStore<RecordyLocalData>,
) : RecordyLocalDataSource {
    override val recordyLocalData = userPreferences.data

    override suspend fun setRecordyLocalData(recordyData: RecordyLocalData) {
        try {
            userPreferences.updateData {
                it.copy(recordyData.a, recordyData.b)
            }
        } catch (ioException: IOException) {
            Log.e("exception", "ioException")
        }
    }
}
