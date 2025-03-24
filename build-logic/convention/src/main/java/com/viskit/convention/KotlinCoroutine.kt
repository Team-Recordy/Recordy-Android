package com.viskit.convention

import com.android.build.api.dsl.CommonExtension
import com.viskit.convention.extension.getBundle
import com.viskit.convention.extension.implementation
import com.viskit.convention.extension.libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureKotlinCoroutine(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        dependencies {
            implementation(libs.getBundle("coroutine"))
        }
    }
}
