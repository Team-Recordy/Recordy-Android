package com.record.auth.source.remote


import com.record.auth.model.request.RequestSignUpDto
import com.record.model.AuthEntity
import com.record.network.model.BaseResponse

interface AuthRemoteDataSource {
    suspend fun signIn(): AuthEntity
    suspend fun signUp(requestSignUpDto: RequestSignUpDto)
    suspend fun checkNickname(nickname: String)
    suspend fun getToken(refreshToken: String): BaseResponse<String>
    suspend fun delete()
    suspend fun logout()
}

