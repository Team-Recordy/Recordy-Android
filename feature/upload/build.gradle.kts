plugins {
    alias(libs.plugins.recordy.feature)
}

android {
    namespace = "com.record.upload"
}

dependencies {
    implementation("com.google.accompanist:accompanist-permissions:0.34.0")
    implementation(projects.domain.upload)
}
