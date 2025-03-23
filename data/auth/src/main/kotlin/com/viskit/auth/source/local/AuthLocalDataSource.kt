package com.viskit.auth.source.local

import com.record.datastore.token.AuthToken
import kotlinx.coroutines.flow.Flow

interface AuthLocalDataSource {
    val userLocalData: Flow<AuthToken>
    suspend fun setAuthLocalData(authToken: AuthToken)
}
