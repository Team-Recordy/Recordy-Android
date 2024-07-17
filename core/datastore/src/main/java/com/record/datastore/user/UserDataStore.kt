package com.record.datastore.token

import android.util.Log
import androidx.datastore.core.DataStore
import com.record.datastore.user.UserData
import java.io.IOException
import javax.inject.Inject

class UserDataStore @Inject constructor(
    private val userPreferences: DataStore<UserData>,
) {
    val user = userPreferences.data

    suspend fun setUserId(user: UserData) {
        try {
            userPreferences.updateData {
                it.copy(userid = user.userid)
            }
        } catch (ioException: IOException) {
            Log.e("exception User", "ioException")
        }
    }
}
