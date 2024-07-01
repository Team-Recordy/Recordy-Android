package com.record.convention

import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.gradle.api.Project

internal fun Project.configureBuildConfig(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        defaultConfig {
            buildConfigField(
                "String",
                "BASE_URL",
                gradleLocalProperties(rootDir, providers).getProperty("base.url")
            )
            buildConfigField(
                "String",
                "KAKAO_NATIVE_KEY",
                gradleLocalProperties(rootDir, providers).getProperty("kakao.native.key")
            )
        }

        buildFeatures {
            buildConfig = true
        }
    }
}
