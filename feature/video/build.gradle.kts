plugins {
    alias(libs.plugins.recordy.feature)
}

android {
    namespace = "com.viskit.video"
}

dependencies {
    implementation(projects.domain.video)
    implementation(projects.domain.user)
    implementation(libs.bundles.media3)
}
