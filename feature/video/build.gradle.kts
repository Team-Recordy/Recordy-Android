plugins {
    alias(libs.plugins.recordy.feature)
}

android {
    namespace = "com.record.video"
}

dependencies {
    implementation(libs.bundles.media3)
}
