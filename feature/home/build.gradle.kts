plugins {
    alias(libs.plugins.recordy.feature)
    alias(libs.plugins.recordy.android.hilt)
}

android {
    namespace = "com.record.home"
}

dependencies {
    implementation(libs.lottie.compose)
    implementation(libs.collapsing.toolbar)
}
