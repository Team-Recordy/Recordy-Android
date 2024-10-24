plugins {
    alias(libs.plugins.recordy.android.compose.library)
}

android {
    namespace = "com.record.designsystem"
}

dependencies {
    implementation(projects.core.ui)
    implementation(projects.core.model)
    implementation(libs.bundles.media3)
    implementation(libs.lottie.compose)
    implementation(project(":domain:user"))
}
