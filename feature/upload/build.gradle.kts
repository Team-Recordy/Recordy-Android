plugins {
    alias(libs.plugins.recordy.feature)
}

android {
    namespace = "com.record.upload"
}

dependencies {
    implementation("com.google.accompanist:accompanist-permissions:0.34.0")
    implementation("com.google.accompanist:accompanist-insets:0.24.13-rc")
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.24.13-rc")
    implementation("com.github.AbedElazizShe:LightCompressor:1.3.2")
    implementation(projects.domain.upload)
    implementation(projects.domain.keyword)
    implementation(projects.core.common)
    implementation("com.amazonaws:aws-android-sdk-mobile-client:2.13.5")
    implementation("com.amazonaws:aws-android-sdk-cognito:2.13.5")
    implementation("com.amazonaws:aws-android-sdk-s3:2.13.5")
}
