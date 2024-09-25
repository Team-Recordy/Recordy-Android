plugins {
    alias(libs.plugins.recordy.feature)
}

android {
    namespace = "com.record.navigator"
}

dependencies {
    implementation(projects.feature.home)
    implementation(projects.feature.login)
    implementation(projects.feature.mypage)
    implementation(projects.feature.profile)
    implementation(projects.feature.upload)
    implementation(projects.feature.video)
    implementation(projects.feature.setting)
    implementation(projects.feature.search)
    implementation(projects.feature.detail)
    implementation(projects.core.common)
}
