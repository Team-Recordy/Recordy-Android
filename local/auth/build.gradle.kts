plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.recordy.data)
    alias(libs.plugins.recordy.android.hilt)
}

android {
    namespace = "com.record.auth"
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.datastore)
    implementation(projects.data.auth)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.bundles.datastore)
}
