plugins {
    alias(libs.plugins.recordy.remote)
}

android {
    namespace = "com.viskit.auth"
}

dependencies {
    implementation(projects.core.network)
    implementation(projects.core.model)
    implementation(projects.data.auth)
}
