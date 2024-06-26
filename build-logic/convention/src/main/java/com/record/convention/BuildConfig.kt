package com.record.convention

import org.gradle.api.Project
import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

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
        }

        buildFeatures {
            buildConfig = true
        }
    }
}