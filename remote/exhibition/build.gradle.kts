plugins {
    alias(libs.plugins.recordy.remote)
}

android {
    namespace = "com.record.exhibition"
}

dependencies {
    implementation(projects.data.exhibition)
}
