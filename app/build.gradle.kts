plugins {
    alias(libs.plugins.recordy.android.application)
    alias(libs.plugins.kotlin.android)
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
}
