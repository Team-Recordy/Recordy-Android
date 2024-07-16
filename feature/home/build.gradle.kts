plugins {
    alias(libs.plugins.recordy.feature)
}

android {
    namespace = "com.record.home"
}

dependencies {
    implementation(projects.domain.video)
    implementation(libs.lottie.compose)
    implementation(libs.collapsing.toolbar)
}
