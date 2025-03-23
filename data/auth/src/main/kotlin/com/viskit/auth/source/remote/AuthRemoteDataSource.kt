package com.viskit.auth.source.remote


import com.viskit.auth.model.request.RequestSignUpDto
import com.viskit.model.AuthEntity
import com.viskit.network.model.BaseResponse

interface AuthRemoteDataSource {
    suspend fun signIn(): AuthEntity
    suspend fun signUp(requestSignUpDto: RequestSignUpDto)
    suspend fun checkNickname(nickname: String)
    suspend fun getToken(refreshToken: String): BaseResponse<String>
    suspend fun delete()
    suspend fun logout()
}

