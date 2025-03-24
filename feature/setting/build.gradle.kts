plugins {
    alias(libs.plugins.recordy.feature)
    alias(libs.plugins.recordy.android.hilt)
}

android {
    namespace = "com.viskit.setting"
}

dependencies {
    implementation(projects.domain.auth)
    implementation(projects.domain.user)
    implementation(projects.domain.video)
    implementation(projects.domain.upload)
    implementation(libs.bundles.accompanist)
}
