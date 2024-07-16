package com.recordy.auth.api

import com.record.auth.model.request.RequestSignInDto
import com.record.auth.model.request.RequestSignUpDto
import com.record.auth.model.response.ResponseSignInDto
import com.record.network.model.BaseResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthService {

    @POST("/$API/$VERSION/$USER/$SIGNIN")
    suspend fun signIn(
        @Header(AUTHORIZATION) authorization: String,
        @Body platformType: RequestSignInDto = RequestSignInDto(KAKAO),
    ): ResponseSignInDto

    @POST("/$API/$VERSION/$USER/$SIGNUP")
    suspend fun signUp(
        @Body body: RequestSignUpDto,
    )

    @GET("/$API/$VERSION/$USER/$CEHCK_NICKNAME")
    suspend fun checkNickname(
        @Query(NICKNAME) nickname: String,
    )

    @POST("/$API/$VERSION/$USER/$TOKEN")
    suspend fun getToken(
        @Header(AUTHORIZATION) authorization: String,
    ): BaseResponse<String>

    @DELETE("/$API/$VERSION/$USER/$DELETE")
    suspend fun delete()

    @DELETE("/$API/$VERSION/$USER/$LOGOUT")
    suspend fun logout()

    companion object {
        const val API = "api"
        const val VERSION = "v1"
        const val USER = "users"
        const val SIGNIN = "signIn"
        const val SIGNUP = "signUp"
        const val CEHCK_NICKNAME = "check-nickname"
        const val NICKNAME = "nickname"
        const val TOKEN = "token"
        const val DELETE = "delete"
        const val LOGOUT = "logout"

        const val AUTHORIZATION = "Authorization"
        const val KAKAO = "KAKAO"
    }
}
