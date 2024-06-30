plugins {
    alias(libs.plugins.recordy.android.library)
    alias(libs.plugins.recordy.android.hilt)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.recordy.plugin.test)
}

android {
    namespace = "com.record.datastore"
}

dependencies {
    implementation(projects.core.common)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.bundles.datastore)
}
