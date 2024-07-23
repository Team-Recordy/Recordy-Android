plugins {
    alias(libs.plugins.recordy.android.library)
    alias(libs.plugins.recordy.android.hilt)
    alias(libs.plugins.recordy.plugin.test)
}

android {
    namespace = "com.record.security"
}

dependencies {
    implementation(projects.core.common)
}
