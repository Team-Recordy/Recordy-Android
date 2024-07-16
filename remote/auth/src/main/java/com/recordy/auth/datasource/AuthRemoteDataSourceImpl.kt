package com.recordy.auth.datasource

import com.record.auth.model.request.RequestSignUpDto
import com.record.auth.model.response.toCoreModel
import com.record.auth.source.remote.AuthRemoteDataSource
import com.recordy.auth.api.AuthService
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
    private val authService: AuthService,
) : AuthRemoteDataSource {
    override suspend fun signIn() = authService.signIn().toCoreModel()

    override suspend fun signUp(requestSignUpDto: RequestSignUpDto) = authService.signUp(requestSignUpDto)

    override suspend fun checkNickname(nickname: String) = authService.checkNickname(nickname)

    override suspend fun getToken(refreshToken: String) = authService.getToken(refreshToken)

    override suspend fun delete() = authService.delete()

    override suspend fun logout() = authService.logout()
}
