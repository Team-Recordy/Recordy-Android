plugins {
    alias(libs.plugins.recordy.feature)
    alias(libs.plugins.recordy.android.hilt)
}

android {
    namespace = "com.record.setting"
}

dependencies {
    implementation(projects.domain.auth)
}
