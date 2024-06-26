package com.record.convention

import com.android.build.api.dsl.CommonExtension
import com.record.convention.extension.debugImplementation
import com.record.convention.extension.getBundle
import com.record.convention.extension.getLibrary
import com.record.convention.extension.getVersion
import com.record.convention.extension.implementation
import com.record.convention.extension.libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        buildFeatures {
            compose = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion = libs.getVersion("compose.compiler").requiredVersion
        }

        dependencies {
            implementation(platform(libs.getLibrary("compose.bom")))
            implementation(libs.getBundle("compose"))
            debugImplementation(libs.getBundle("compose.debug"))
        }
    }
}