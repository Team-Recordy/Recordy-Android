package com.viskit.user.datasource

import com.viskit.datastore.user.UserData
import com.viskit.datastore.user.UserDataStore
import com.viskit.user.source.local.UserLocalDataSource
import com.viskit.video.source.local.LocalUserInfoDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UserLocalDataSourceImpl @Inject constructor(
    private val userLocalDataSource: UserDataStore,
) : UserLocalDataSource, LocalUserInfoDataSource {
    override val userLocalData: Flow<UserData> = userLocalDataSource.user
    override suspend fun setUserLocalData(userData: UserData) {
        userLocalDataSource.setUserId(userData)
    }

    override suspend fun getMyId(): Long = userLocalData.first().userid
}
