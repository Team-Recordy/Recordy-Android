package com.record.auth.repository

import com.record.auth.model.request.toData
import com.record.auth.source.local.AuthLocalDataSource
import com.record.auth.source.remote.AuthRemoteDataSource
import com.recordy.auth.model.AuthAgreementEntity
import com.recordy.auth.model.AuthToken
import com.recordy.auth.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authLocalDataSource: AuthLocalDataSource,
    private val authRemoteDataSource: AuthRemoteDataSource,
) : AuthRepository {
    override suspend fun signIn(accessToken: String): Result<AuthToken> = runCatching {
        authRemoteDataSource.signIn(accessToken).data!!.toDomain()
    }

    override suspend fun signUp(accessToken: String, authAgreementEntity: AuthAgreementEntity): Result<Unit> = runCatching {
        authRemoteDataSource.signUp(accessToken, requestSignUpDto = authAgreementEntity.toData())
    }

    override suspend fun checkNickname(accessToken: String, nickname: String): Result<Unit> =
        runCatching { authRemoteDataSource.checkNickname(accessToken = accessToken, nickname = nickname) }

    override suspend fun delete(accessToken: String): Result<Unit> = runCatching {
        authRemoteDataSource.delete(accessToken)
    }

    override suspend fun logout(accessToken: String): Result<Unit> = runCatching {
        authRemoteDataSource.logout(accessToken)
    }

}
