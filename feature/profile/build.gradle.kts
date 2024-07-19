plugins {
    alias(libs.plugins.recordy.feature)
}

android {
    namespace = "com.record.profile"
}

dependencies {
    implementation(projects.domain.user)
    implementation(projects.domain.video)
    implementation(projects.domain.upload)
    implementation(projects.feature.upload)
}
