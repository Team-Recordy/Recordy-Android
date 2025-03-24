plugins {
    alias(libs.plugins.recordy.remote)
}

android {
    namespace = "com.viskit.exhibition"
}

dependencies {
    implementation(projects.data.exhibition)
}
