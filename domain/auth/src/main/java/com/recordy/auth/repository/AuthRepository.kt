package com.recordy.auth.repository

import com.recordy.auth.model.AuthAgreementEntity
import com.recordy.auth.model.AuthEntity

interface AuthRepository {
    suspend fun signIn(accessToken: String): Result<AuthEntity>
    suspend fun signUp(accessToken: String, authAgreementEntity: AuthAgreementEntity): Result<Unit>
    suspend fun checkNickname(accessToken: String, nickname: String): Result<Unit>
    suspend fun delete(accessToken: String): Result<Unit>
    suspend fun logout(accessToken: String): Result<Unit>
}
