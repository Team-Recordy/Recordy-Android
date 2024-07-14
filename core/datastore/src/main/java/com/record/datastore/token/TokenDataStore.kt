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
                it.copy(authToken.accessToken, authToken.refreshToken)
            }
        } catch (ioException: IOException) {
            Log.e("exception", "ioException")
        }
    }
}
