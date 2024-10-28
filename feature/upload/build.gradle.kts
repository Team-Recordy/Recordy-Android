plugins {
    alias(libs.plugins.recordy.feature)
    alias(libs.plugins.kotlin.serialization)
    id("kotlin-parcelize")
}

android {
    namespace = "com.record.upload"
}

dependencies {
    implementation(libs.bundles.aws)
    implementation(libs.bundles.accompanist)
    implementation(libs.lightcompressor)
    implementation(projects.domain.upload)
    implementation(projects.domain.keyword)
    implementation(projects.core.common)
    implementation(libs.lottie.compose)
    implementation(projects.domain.exhibition)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.bundles.compose)
}
