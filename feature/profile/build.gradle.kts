plugins {
    alias(libs.plugins.recordy.feature)
}

android {
    namespace = "com.record.profile"
}

dependencies {
    implementation(projects.feature.video)
}
