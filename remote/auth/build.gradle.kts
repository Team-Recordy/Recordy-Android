plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.recordy.data)
    alias(libs.plugins.recordy.android.hilt)
}

android {
    namespace = "com.recordy.auth"
}

dependencies {
    implementation(projects.core.network)
    implementation(projects.core.model)
    implementation(projects.core.datastore)
    implementation(projects.data.recordy)
    implementation(projects.data.auth)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
}
