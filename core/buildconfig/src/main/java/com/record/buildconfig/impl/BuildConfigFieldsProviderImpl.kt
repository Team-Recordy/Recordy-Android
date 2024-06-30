package com.record.buildconfig.impl

import com.record.buildconfig.BuildConfig.BASE_URL
import com.record.buildconfig.BuildConfig.DEBUG
import com.record.common.buildconfig.BuildConfigFieldProvider
import com.record.common.buildconfig.BuildConfigFields

class BuildConfigFieldsProviderImpl : BuildConfigFieldProvider {
    override fun get(): BuildConfigFields =
        BuildConfigFields(
            baseUrl = BASE_URL,
            isDebug = DEBUG
        )
}
