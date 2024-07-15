package com.record.network

import com.record.datastore.token.TokenDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class AuthenticationIntercept @Inject constructor(
    private val datastore: TokenDataStore,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val authRequest = handleRequest(originalRequest)
        val response = chain.proceed(authRequest)

        return response
    }

    private fun handleRequest(originalRequest: Request) =
        when {
            originalRequest.url.encodedPath.contains(USER) -> {
                when {
                    originalRequest.url.encodedPath.contains(SIGNUP) ||
                        originalRequest.url.encodedPath.contains(CEHCK_NICKNAME) ||
                        originalRequest.url.encodedPath.contains(NICKNAME) ||
                        originalRequest.url.encodedPath.contains(LOGOUT) ||
                        originalRequest.url.encodedPath.contains(DELETE)
                    -> {
                        originalRequest.accessTokenBuilder()
                    }

                    originalRequest.url.encodedPath.contains(TOKEN) -> {
                        originalRequest.refreshTokenBuilder()
                    }

                    else -> {
                        originalRequest.accessTokenBuilder()
                    }
                }
            }

            else -> {
                originalRequest
            }
        }

    private fun Request.accessTokenBuilder() =
        this.newBuilder().addHeader("accessToken", runBlocking { datastore.token.first().accessToken }).build()

    private fun Request.refreshTokenBuilder() =
        this.newBuilder().addHeader("refreshToken", runBlocking { datastore.token.first().refreshToken }).build()

    companion object {
        const val API = "api"
        const val USER = "users"
        const val SIGNUP = "signUp"
        const val CEHCK_NICKNAME = "check-nickname"
        const val NICKNAME = "nickname"
        const val TOKEN = "token"
        const val DELETE = "delete"
        const val LOGOUT = "logout"
    }
}
