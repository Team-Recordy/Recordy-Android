plugins {
    alias(libs.plugins.recordy.feature)
}

android {
    namespace = "com.record.detail"
}
dependencies {
    implementation(projects.domain.video)
}
