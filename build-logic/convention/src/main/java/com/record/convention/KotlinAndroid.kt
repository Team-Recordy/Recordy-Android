package com.record.convention

import com.android.build.api.dsl.CommonExtension
import com.record.convention.extension.getVersion
import com.record.convention.extension.libs
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.support.kotlinCompilerOptions
import org.gradle.process.internal.JvmOptions
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
import org.jetbrains.kotlin.gradle.tasks.CompilerPluginOptions

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

        extensions.getByType<KotlinAndroidProjectExtension>().configureCompilerOptions()

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
    }
}

fun KotlinAndroidProjectExtension.configureCompilerOptions() {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}