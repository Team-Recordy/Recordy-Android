plugins {
    alias(libs.plugins.recordy.android.compose.library)
}

android {
    namespace = "com.record.designsystem"
}

dependencies {
    implementation(projects.core.ui)
}
