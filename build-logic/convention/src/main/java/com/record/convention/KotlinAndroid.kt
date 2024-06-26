package com.record.convention

import com.android.build.api.dsl.CommonExtension
import com.record.convention.extension.getVersion
import com.record.convention.extension.libs
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    pluginManager.apply("org.jetbrains.kotlin.android")

    commonExtension.apply {
        compileSdk = libs.getVersion("compileSdk").requiredVersion.toInt()

        defaultConfig {
            minSdk = libs.getVersion("minSdk").requiredVersion.toInt()

            vectorDrawables.useSupportLibrary = true
        }

        compileOptions {
            sourceCompatibility = Const.JAVA_VERSION
            targetCompatibility = Const.JAVA_VERSION
        }

        kotlinOptions {
            jvmTarget = libs.getVersion("jvmVersion").requiredVersion
        }

        buildTypes {
            getByName("debug") {
                proguardFiles(
                    getDefaultProguardFile("proguard-android.txt"),
                    "proguard-debug.pro",
                )
            }

            getByName("release") {
                isMinifyEnabled = true
                proguardFiles(
                    getDefaultProguardFile("proguard-android.txt"),
                    "proguard-rules.pro",
                )
            }
        }

        buildFeatures {
            buildConfig = true
        }
    }
}

internal fun CommonExtension<*, *, *, *, *, *>.kotlinOptions(
    block: KotlinJvmOptions.() -> Unit,
) {
    (this as ExtensionAware).extensions.configure("kotlinOptions", block)
}