package com.record.recordy

import android.app.Application
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import com.record.buildconfig.BuildConfig.KAKAO_NATIVE_KEY
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.components.SingletonComponent

@HiltAndroidApp
class RecordyApplication : Application(), Configuration.Provider {
    override fun onCreate() {
        super.onCreate()
        setKakaoSdk()
        val keyHash = Utility.getKeyHash(this)
        Log.d("키해시", " $keyHash")
    }

    private fun setKakaoSdk() {
        KakaoSdk.init(this, KAKAO_NATIVE_KEY)
    }

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface HiltWorkerFactoryEntryPoint {
        fun workerFactory(): HiltWorkerFactory
    }

    override val workManagerConfiguration: Configuration = Configuration.Builder()
        .setWorkerFactory(EntryPoints.get(this, HiltWorkerFactoryEntryPoint::class.java).workerFactory())
        .build()
}
