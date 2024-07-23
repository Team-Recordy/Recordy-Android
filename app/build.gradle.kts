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
    implementation(projects.core.cache)
    implementation(projects.core.security)
    implementation(projects.data.auth)
    implementation(projects.data.oauth)
    implementation(projects.data.video)
    implementation(projects.data.user)
    implementation(projects.data.keyword)
    implementation(projects.local.auth)
    implementation(projects.local.user)
    implementation(projects.remote.auth)
    implementation(projects.remote.user)
    implementation(projects.remote.video)
    implementation(projects.remote.keyword)
    implementation(projects.feature.navigator)
    implementation(libs.kakao.login)
}
