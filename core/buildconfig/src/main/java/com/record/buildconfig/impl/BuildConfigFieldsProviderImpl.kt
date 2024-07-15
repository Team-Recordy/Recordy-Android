package com.record.buildconfig.impl

import com.record.buildconfig.BuildConfig.BASE_URL
import com.record.buildconfig.BuildConfig.DEBUG
import com.record.buildconfig.BuildConfig.KAKAO_NATIVE_KEY
import com.record.common.buildconfig.BuildConfigFieldProvider
import com.record.common.buildconfig.BuildConfigFields
import javax.inject.Inject

class BuildConfigFieldsProviderImpl @Inject constructor() : BuildConfigFieldProvider {
    override fun get(): BuildConfigFields =
        BuildConfigFields(
            baseUrl = BASE_URL,
            kakaoNativeKey = KAKAO_NATIVE_KEY,
            isDebug = DEBUG,
        )
}
