package com.record.auth.source.local

import com.recordy.auth.model.AuthToken
import kotlinx.coroutines.flow.Flow

interface AuthLocalDataSource {
    val userLocalData: Flow<AuthToken>
    suspend fun setAuthLocalData(authToken: AuthToken)
}
