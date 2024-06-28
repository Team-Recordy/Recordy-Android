package org.sopt.datastore.source

import android.util.Log
import androidx.datastore.core.DataStore
import org.sopt.datastore.UserData
import java.io.IOException
import javax.inject.Inject

class UserPreferencesDataSource @Inject constructor(
    private val userPreferences: DataStore<UserData>,
) {
    val userData = userPreferences.data

    suspend fun setUserData(user: UserData) {
        try {
            userPreferences.updateData {
                it.copy(user.name, user.isAutoLogin, user.accessToken, user.refreshToken)
            }
        } catch (ioException: IOException) {
            Log.e("exception", "setUserData ioException")
        }
    }

    suspend fun setAutoLogin(isChecked: Boolean) {
        try {
            userPreferences.updateData {
                it.copy(isAutoLogin = isChecked)
            }
        } catch (ioException: IOException) {
            Log.e("exception", "setAutoLogin ioException")
        }
    }
}