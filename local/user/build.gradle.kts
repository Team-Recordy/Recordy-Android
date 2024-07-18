plugins {
    alias(libs.plugins.recordy.local)
}

android {
    namespace = "com.record.user"
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.datastore)
    implementation(projects.data.user)
    implementation(projects.data.video)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.bundles.datastore)
}
