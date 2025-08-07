plugins {
    alias(libs.plugins.recordy.feature)
}

android {
    namespace = "com.viskit.login"
}
dependencies {
    implementation(projects.core.amplitude)
    implementation(projects.domain.oauth)
    implementation(projects.domain.auth)
    implementation(projects.domain.user)
}
