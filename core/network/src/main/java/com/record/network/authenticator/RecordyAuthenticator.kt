package com.record.network.authenticator

import android.content.Context
import com.jakewharton.processphoenix.ProcessPhoenix
import com.record.datastore.token.TokenDataStore
import com.record.network.TokenRefreshService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class RecordyAuthenticator @Inject constructor(
    private val dataStore: TokenDataStore,
    private val tokenRefreshService: TokenRefreshService,
    @ApplicationContext private val context: Context,
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.code == CODE_TOKEN_EXPIRED) {
            val newTokens = runCatching {
                runBlocking {
                    tokenRefreshService.postAuthRefresh(dataStore.token.first().refreshToken)
                }
            }.onSuccess {
                runBlocking {
                    dataStore.apply {
                        setAccessToken(it.data?.accessToken ?: "")
                    }
                }
            }.onFailure {
                runBlocking {
                    dataStore.setAutoLogin(false)
                }
                ProcessPhoenix.triggerRebirth(context)
            }.getOrThrow()

            return response.request.newBuilder()
                .header("accessToken", newTokens.data?.accessToken ?: "")
                .build()
        }
        return null
    }

    companion object {
        const val CODE_TOKEN_EXPIRED = 401
    }
}
