plugins {
    alias(libs.plugins.recordy.feature)
    alias(libs.plugins.recordy.android.hilt)
}

android {
    namespace = "com.record.login"
}
dependencies {
    implementation(projects.domain.oauth)
}
