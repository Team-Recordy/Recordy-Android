plugins {
    alias(libs.plugins.recordy.feature)
}

android {
    namespace = "com.record.home"
}

dependencies {
    implementation(projects.domain.video)
    implementation(projects.domain.keyword)
    implementation(projects.domain.exhibition)
    implementation(libs.lottie.compose)
    implementation(libs.collapsing.toolbar)
    implementation(libs.google.location)
}
