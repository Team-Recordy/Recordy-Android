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
    implementation(projects.domain.upload)
}
