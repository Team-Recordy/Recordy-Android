plugins {
    alias(libs.plugins.recordy.data)
}

android {
    namespace = "com.record.auth"
}

dependencies {
    implementation(projects.core.datastore)
    implementation(projects.domain.auth)

    implementation(libs.kakao.login)
}
