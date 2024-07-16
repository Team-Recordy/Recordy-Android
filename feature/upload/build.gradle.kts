plugins {
    alias(libs.plugins.recordy.feature)
}

android {
    namespace = "com.record.upload"
}

dependencies {
    implementation(projects.domain.upload)
}
