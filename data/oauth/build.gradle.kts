plugins {
    alias(libs.plugins.recordy.data)
}

android {
    namespace = "com.record.oauth"
}

dependencies {
    implementation(projects.core.datastore)
    implementation(projects.domain.oauth)

    implementation(libs.kakao.login)
}
