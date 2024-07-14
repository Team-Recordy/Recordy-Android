package com.recordy.auth.api

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
        @Body platformType: String = KAKAO,
    ): BaseResponse<ResponseSignInDto>

    @POST("/$API/$VERSION/$USER/$SIGNUP")
    suspend fun signUp(
        @Header(AUTHORIZATION) authorization: String,
        @Body body: RequestSignUpDto,
    ): BaseResponse<Unit>

    @GET("/$API/$VERSION/$USER/$CEHCK_NICKNAME")
    suspend fun checkNickname(
        @Header(AUTHORIZATION) authorization: String,
        @Query(NICKNAME) nickname: String,
    ): BaseResponse<Unit>

    @POST("/$API/$VERSION/$USER/$TOKEN")
    suspend fun getToken(
        @Header(AUTHORIZATION) authorization: String,
    ): BaseResponse<String>

    @DELETE("/$API/$VERSION/$USER/$DELETE")
    suspend fun delete(
        @Header(AUTHORIZATION) authorization: String,
    ): BaseResponse<Unit>

    @DELETE("/$API/$VERSION/$USER/$LOGOUT")
    suspend fun logout(
        @Header(AUTHORIZATION) authorization: String,
    ): BaseResponse<Unit>

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
