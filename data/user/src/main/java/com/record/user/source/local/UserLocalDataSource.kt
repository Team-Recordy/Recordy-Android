package com.record.user.source.local

import com.record.datastore.user.UserData
import kotlinx.coroutines.flow.Flow

interface UserLocalDataSource {
    val userLocalData: Flow<UserData>
    suspend fun setUserLocalData(userData: UserData)
}
