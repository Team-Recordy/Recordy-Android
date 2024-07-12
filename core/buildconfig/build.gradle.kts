plugins {
    alias(libs.plugins.recordy.android.library)
    alias(libs.plugins.recordy.android.hilt)
    alias(libs.plugins.recordy.plugin.test)
    alias(libs.plugins.recordy.plugin.build.config)
}

android {
    namespace = "com.record.buildconfig"
}

dependencies {
    implementation(projects.core.common)
    implementation(libs.kakao.login)
}
