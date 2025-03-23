package com.viskit.auth.repository

import com.viskit.model.AuthEntity
import com.viskit.auth.model.AuthAgreementEntity

interface AuthRepository {
    suspend fun signIn(): Result<AuthEntity>
    suspend fun signUp(authAgreementEntity: AuthAgreementEntity): Result<Unit>
    suspend fun checkNickname(nickname: String): Result<Unit>
    suspend fun delete(): Result<Unit>
    suspend fun logout(): Result<Unit>

    suspend fun saveLocalData(authToken: AuthEntity): Result<Unit>
    suspend fun getLocalData(): Result<AuthEntity>
}
