plugins {
    alias(libs.plugins.recordy.android.library)
    alias(libs.plugins.recordy.android.hilt)
}

android {
    namespace = "com.viskit.amplitude"
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.buildconfig)
}
