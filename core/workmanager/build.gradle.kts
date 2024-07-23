plugins {
    alias(libs.plugins.recordy.android.library)
    alias(libs.plugins.recordy.android.hilt)
}

android {
    namespace = "com.record.workmanager"
}

dependencies {
    implementation(projects.core.common)
    implementation(libs.hilt.androidx.common)
    implementation(libs.hilt.androidx.work)
    implementation(libs.androidx.workmanager)
    ksp(libs.hilt.androidx.compiler)
}
