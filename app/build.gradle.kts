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
    implementation(projects.data.oauth)
    implementation(projects.data.auth)
    implementation(projects.local.auth)
    implementation(projects.remote.auth)
    implementation(projects.domain.oauth)
    implementation(projects.domain.auth)
    implementation(projects.feature.navigator)
    implementation(libs.kakao.login)
}
