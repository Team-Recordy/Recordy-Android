package com.record.recordy

import android.app.Application
import android.util.Log
import com.kakao.sdk.common.util.Utility
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RecordyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val keyHash = Utility.getKeyHash(this)
        Log.d("키해시", " $keyHash")
    }
}
