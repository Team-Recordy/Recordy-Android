package com.record.convention.extension

import org.gradle.api.Project
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.file.ConfigurableFileTree
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.DependencyHandlerScope

fun DependencyHandlerScope.implementation(project: Project) {
    "implementation"(project)
}

fun DependencyHandlerScope.implementation(provider: Provider<*>) {
    "implementation"(provider)
}

fun DependencyHandlerScope.implementation(fileTree: ConfigurableFileTree) {
    "implementation"(fileTree)
}

fun DependencyHandlerScope.implementation(fileCollection: ConfigurableFileCollection) {
    "implementation"(fileCollection)
}

fun DependencyHandlerScope.debugImplementation(provider: Provider<*>) {
    "debugImplementation"(provider)
}

fun DependencyHandlerScope.releaseImplementation(provider: Provider<*>) {
    "releaseImplementation"(provider)
}

fun DependencyHandlerScope.ksp(provider: Provider<*>) {
    "ksp"(provider)
}

fun DependencyHandlerScope.kspTest(provider: Provider<*>) {
    "kspTest"(provider)
}

fun DependencyHandlerScope.coreLibraryDesugaring(provider: Provider<*>) {
    "coreLibraryDesugaring"(provider)
}

fun DependencyHandlerScope.androidTestImplementation(provider: Provider<*>) {
    "androidTestImplementation"(provider)
}

fun DependencyHandlerScope.testImplementation(provider: Provider<*>) {
    "testImplementation"(provider)
}

fun DependencyHandlerScope.compileOnly(provider: Provider<*>) {
    "compileOnly"(provider)
}