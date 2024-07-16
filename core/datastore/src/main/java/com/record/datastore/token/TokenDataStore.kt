package com.record.datastore.token

import android.util.Log
import androidx.datastore.core.DataStore
import java.io.IOException
import javax.inject.Inject

class TokenDataStore @Inject constructor(
    private val tokenPreferences: DataStore<AuthToken>,
) {
    val token = tokenPreferences.data

    suspend fun setAuthToken(authToken: AuthToken) {
        try {
            tokenPreferences.updateData {
                it.copy(authToken.accessToken, authToken.refreshToken, authToken.isSigned)
            }
        } catch (ioException: IOException) {
            Log.e("exception", "ioException")
        }
    }

    suspend fun setAutoLogin(boolean: Boolean) {
        try {
            tokenPreferences.updateData {
                it.copy(isSigned = boolean)
            }
        } catch (ioException: IOException) {
            Log.e("exception", "ioException")
        }
    }

    suspend fun setAccessToken(accessToken: String) {
        try {
            tokenPreferences.updateData {
                it.copy(accessToken = accessToken)
            }
        } catch (ioException: IOException) {
            Log.e("exception", "ioException")
        }
    }

    suspend fun setRefreshToken(refreshToken: String) {
        try {
            tokenPreferences.updateData {
                it.copy(refreshToken = refreshToken)
            }
        } catch (ioException: IOException) {
            Log.e("exception", "ioException")
        }
    }
}
