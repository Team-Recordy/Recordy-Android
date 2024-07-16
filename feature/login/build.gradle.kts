plugins {
    alias(libs.plugins.recordy.feature)
}

android {
    namespace = "com.record.login"
}
dependencies {
    implementation(projects.domain.oauth)
    implementation(projects.domain.auth)
}
