plugins {
    alias(libs.plugins.recordy.data)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.record.video"
}

dependencies {
    implementation(projects.domain.video)
    implementation(projects.domain.upload)
    implementation(projects.core.workmanager)
    implementation(libs.hilt.androidx.common)
    implementation(libs.hilt.androidx.work)
    implementation(libs.androidx.workmanager)
    ksp(libs.hilt.androidx.compiler)
}
