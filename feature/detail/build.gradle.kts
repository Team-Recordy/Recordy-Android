plugins {
    alias(libs.plugins.recordy.feature)
}

android {
    namespace = "com.viskit.detail"
}
dependencies {
    implementation(projects.domain.video)
    implementation(projects.domain.exhibition)
}
