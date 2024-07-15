package com.recordy.auth.repository

import com.record.model.AuthEntity
import com.recordy.auth.model.AuthAgreementEntity

interface AuthRepository {
    suspend fun signIn(accessToken: String): Result<AuthEntity>
    suspend fun signUp(authAgreementEntity: AuthAgreementEntity): Result<Unit>
    suspend fun checkNickname(nickname: String): Result<Unit>
    suspend fun delete(accessToken: String): Result<Unit>
    suspend fun logout(accessToken: String): Result<Unit>

    suspend fun saveLocalData(authToken: AuthEntity): Result<Unit>
    suspend fun getLocalData(): Result<AuthEntity>
}
