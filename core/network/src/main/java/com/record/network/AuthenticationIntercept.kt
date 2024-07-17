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

    private fun handleRequest(originalRequest: Request) = originalRequest.accessTokenBuilder()

    private fun Request.accessTokenBuilder() =
        this.newBuilder().addHeader("Authorization", runBlocking { "Bearer ${datastore.token.first().accessToken}" }).build()
}
