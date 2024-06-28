package com.record.build_config.impl

import com.record.build_config.BuildConfig.BASE_URL
import com.record.build_config.BuildConfig.DEBUG
import com.record.common.buildconfig.BuildConfigFieldProvider
import com.record.common.buildconfig.BuildConfigFields

class BuildConfigFieldsProviderImpl: BuildConfigFieldProvider {
    override fun get(): BuildConfigFields =
        BuildConfigFields(
            baseUrl = BASE_URL,
            isDebug = DEBUG
        )
}