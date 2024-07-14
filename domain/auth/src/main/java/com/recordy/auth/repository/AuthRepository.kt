package com.recordy.auth.repository

import com.recordy.auth.model.AuthAgreementEntity
import com.recordy.auth.model.AuthToken

interface AuthRepository {
    suspend fun signIn(platformType: String): Result<AuthToken>
    suspend fun signUp(accessToken: String, authAgreementEntity: AuthAgreementEntity): Result<Unit>
    suspend fun checkNickname(accessToken: String, nickname: String): Result<Unit>
    suspend fun delete(accessToken: String): Result<Unit>
    suspend fun logout(accessToken: String): Result<Unit>
}
