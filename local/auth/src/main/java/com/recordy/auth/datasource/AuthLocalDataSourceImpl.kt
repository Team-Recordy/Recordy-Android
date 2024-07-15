package com.recordy.auth.datasource

import com.record.auth.source.local.AuthLocalDataSource
import com.record.datastore.token.AuthToken
import com.record.datastore.token.TokenDataStore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthLocalDataSourceImpl @Inject constructor(
    private val authLocalDataSource: TokenDataStore,
) : AuthLocalDataSource {
    override val userLocalData: Flow<AuthToken> = authLocalDataSource.token
    override suspend fun setAuthLocalData(authToken: AuthToken) {
        authLocalDataSource.setAuthToken(authToken = authToken)
    }
}
