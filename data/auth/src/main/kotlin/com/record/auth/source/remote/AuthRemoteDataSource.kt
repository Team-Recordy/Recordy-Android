package com.record.auth.source.remote


import com.record.auth.model.request.RequestSignUpDto
import com.record.auth.model.response.ResponseSignInDto
import com.record.network.model.BaseResponse

interface AuthRemoteDataSource {
    suspend fun signIn(accessToken: String): BaseResponse<ResponseSignInDto>
    suspend fun signUp(accessToken: String, requestSignUpDto: RequestSignUpDto): BaseResponse<Unit>
    suspend fun checkNickname(accessToken: String, nickname: String): BaseResponse<Unit>
    suspend fun getToken(refreshToken: String): BaseResponse<String>
    suspend fun delete(accessToken: String): BaseResponse<Unit>
    suspend fun logout(accessToken: String): BaseResponse<Unit>
}

