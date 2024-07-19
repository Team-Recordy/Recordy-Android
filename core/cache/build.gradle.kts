plugins {
    alias(libs.plugins.recordy.android.library)
    alias(libs.plugins.recordy.android.hilt)
}

android {
    namespace = "com.record.cache"
}

dependencies {
    implementation(libs.bundles.media3)
}
