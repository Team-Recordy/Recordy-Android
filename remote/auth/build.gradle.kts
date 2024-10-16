plugins {
    alias(libs.plugins.recordy.remote)
}

android {
    namespace = "com.recordy.auth"
}

dependencies {
    implementation(projects.core.network)
    implementation(projects.core.model)
    implementation(projects.data.auth)
}
