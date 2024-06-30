plugins {
    alias(libs.plugins.recordy.data)
}

android {
    namespace = "com.record.recordy"
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.datastore)
    implementation(projects.data.recordy)

    implementation(libs.bundles.datastore)
}
