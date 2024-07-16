plugins {
    alias(libs.plugins.recordy.remote)
}

android {
    namespace = "com.record.user"
}

dependencies {
    implementation(projects.data.user)
}
