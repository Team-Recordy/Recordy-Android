package com.record.network

import com.record.network.model.BaseResponse
import com.record.network.model.ResponsePostAuthRefreshDto
import retrofit2.http.Header
import retrofit2.http.POST

interface TokenRefreshService {
    companion object {
        const val API = "api"
        const val VERSION = "v1"
        const val USER = "users"
        const val TOKEN = "token"
    }

    @POST("/$API/$VERSION/$USER/$TOKEN")
    suspend fun postAuthRefresh(
        @Header("refreshToken") refreshToken: String,
    ): BaseResponse<ResponsePostAuthRefreshDto>
}
