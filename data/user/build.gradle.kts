plugins {
    alias(libs.plugins.recordy.data)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.viskit.user"
}

dependencies {
    implementation(projects.domain.user)
    implementation(projects.core.datastore)
    implementation(projects.data.video)
}
