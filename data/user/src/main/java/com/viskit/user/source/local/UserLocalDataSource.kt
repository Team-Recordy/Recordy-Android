package com.viskit.user.source.local

import com.viskit.datastore.user.UserData
import kotlinx.coroutines.flow.Flow

interface UserLocalDataSource {
    val userLocalData: Flow<UserData>
    suspend fun setUserLocalData(userData: UserData)
}
