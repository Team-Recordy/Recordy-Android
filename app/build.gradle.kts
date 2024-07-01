plugins {
    alias(libs.plugins.recordy.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.recordy.android.hilt)
}

android {
    namespace = "com.record.recordy"
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.designsystem)
    implementation(projects.core.buildconfig)
    implementation(projects.core.datastore)
    implementation(projects.core.network)
    implementation(projects.feature.navigator)
    implementation(libs.kakao.login)
}
