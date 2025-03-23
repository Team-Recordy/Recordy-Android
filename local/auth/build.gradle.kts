plugins {
    alias(libs.plugins.recordy.local)
}

android {
    namespace = "com.viskit.auth"
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.datastore)
    implementation(projects.data.auth)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.bundles.datastore)
}
