plugins {
    alias(libs.plugins.recordy.feature)
}

android {
    namespace = "com.record.mypage"
}

dependencies {
    implementation(projects.domain.user)
    implementation(projects.domain.video)
    implementation(projects.feature.upload)
}
