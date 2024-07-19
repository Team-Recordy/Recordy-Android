package com.record.recordy

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.record.buildconfig.BuildConfig.KAKAO_NATIVE_KEY
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RecordyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        setKakaoSdk()
    }

    private fun setKakaoSdk() {
        KakaoSdk.init(this, KAKAO_NATIVE_KEY)
    }
}
