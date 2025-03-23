package com.viskit.network.authenticator

import android.content.Context
import com.jakewharton.processphoenix.ProcessPhoenix
import com.viskit.datastore.token.TokenDataStore
import com.viskit.network.TokenRefreshService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
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
    private val mutex = Mutex()

    override fun authenticate(route: Route?, response: Response): Request? {
        return if (response.code == CODE_TOKEN_EXPIRED) {
            runBlocking {
                mutex.withLock {
                    processTokenRefresh(response)
                }
            }
        } else {
            null
        }
    }

    private fun processTokenRefresh(response: Response): Request? {
        val newTokens = runCatching {
            runBlocking {
                tokenRefreshService.postAuthRefresh(dataStore.token.first().refreshToken)
            }
        }.onSuccess {
            runBlocking {
                dataStore.setAccessToken(it.accessToken ?: "")
            }
        }.onFailure {
            runBlocking {
                dataStore.setAutoLogin(false)
            }
            ProcessPhoenix.triggerRebirth(context)
        }.getOrThrow()

        return response.request.newBuilder()
            .header("accessToken", newTokens.accessToken ?: "")
            .build()
    }

    companion object {
        const val CODE_TOKEN_EXPIRED = 401
    }
}
