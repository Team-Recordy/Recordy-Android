package com.record.auth.repository

import com.record.auth.model.request.toData
import com.record.auth.source.local.AuthLocalDataSource
import com.record.auth.source.remote.AuthRemoteDataSource
import com.record.datastore.token.AuthToken
import com.record.model.AuthEntity
import com.record.model.exception.ApiError
import com.recordy.auth.model.AuthAgreementEntity
import com.recordy.auth.repository.AuthRepository
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authLocalDataSource: AuthLocalDataSource,
    private val authRemoteDataSource: AuthRemoteDataSource,
) : AuthRepository {
    companion object {
        const val ERROR_300 = "리다이렉션 오류"
        const val ERROR_400 = "무슨무슨 오류"
        const val ERROR_401 = "토큰 오류"
        const val ERROR_402 = "무슨무슨 오류"
        const val ERROR_403 = "무슨무슨 오류"
        const val ERROR_404 = "히히 못찾겠지 오류"
        const val ERROR_SERVER = "서버 오류"
    }

    override suspend fun signIn(accessToken: String): Result<AuthEntity> = runCatching {
        authRemoteDataSource.signIn("Bearer $accessToken")
    }.recoverCatching { exception ->
        when (exception) {
            is HttpException -> throw ApiError(getErrorMessage(exception.code()))
            else -> throw exception
        }
    }

    override suspend fun signUp(authAgreementEntity: AuthAgreementEntity): Result<Unit> = runCatching {
        authRemoteDataSource.signUp(requestSignUpDto = authAgreementEntity.toData())
    }.recoverCatching { exception ->
        when (exception) {
            is HttpException -> throw ApiError(getErrorMessage(exception.code()))
            else -> throw exception
        }
    }

    override suspend fun checkNickname( nickname: String): Result<Unit> = runCatching {
        authRemoteDataSource.checkNickname( nickname = nickname)
    }.recoverCatching { exception ->
        when (exception) {
            is HttpException -> throw ApiError(getErrorMessage(exception.code()))
            else -> throw exception
        }
    }

    override suspend fun delete(accessToken: String): Result<Unit> = runCatching {
        authRemoteDataSource.delete(accessToken)
        Unit
    }.recoverCatching { exception ->
        when (exception) {
            is HttpException -> throw ApiError(getErrorMessage(exception.code()))
            else -> throw exception
        }
    }

    override suspend fun logout(accessToken: String): Result<Unit> = runCatching {
        authRemoteDataSource.logout(accessToken)
        Unit
    }.recoverCatching { exception ->
        when (exception) {
            is HttpException -> throw ApiError(getErrorMessage(exception.code()))
            else -> throw exception
        }
    }

    override suspend fun saveLocalData(authToken: AuthEntity): Result<Unit> = runCatching {
        authLocalDataSource.setAuthLocalData(AuthToken(authToken.accessToken, authToken.refreshToken, authToken.isSignedUp))
    }.recoverCatching { exception ->
        throw exception
    }

    override suspend fun getLocalData(): Result<AuthEntity> = runCatching {
        AuthEntity(
            authLocalDataSource.userLocalData.first().accessToken,
            authLocalDataSource.userLocalData.first().refreshToken,
            authLocalDataSource.userLocalData.first().isSigned,
        )
    }.recoverCatching { exception ->
        throw exception
    }

    private fun getErrorMessage(code: Int): String {
        return when (code) {
            in 300..399 -> ERROR_300
            400 -> ERROR_400
            401 -> ERROR_401
            402 -> ERROR_402
            403 -> ERROR_403
            404 -> ERROR_404
            else -> ERROR_SERVER
        }
    }
}
