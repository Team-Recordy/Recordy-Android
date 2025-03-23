package com.viskit.buildconfig.impl

import com.viskit.buildconfig.BuildConfig.BASE_URL
import com.viskit.buildconfig.BuildConfig.DEBUG
import com.viskit.buildconfig.BuildConfig.KAKAO_NATIVE_KEY
import com.viskit.common.buildconfig.BuildConfigFieldProvider
import com.viskit.common.buildconfig.BuildConfigFields
import javax.inject.Inject

class BuildConfigFieldsProviderImpl @Inject constructor() : BuildConfigFieldProvider {
    override fun get(): BuildConfigFields =
        BuildConfigFields(
            baseUrl = BASE_URL,
            kakaoNativeKey = KAKAO_NATIVE_KEY,
            isDebug = DEBUG,
        )
}
