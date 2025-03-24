plugins {
    alias(libs.plugins.recordy.remote)
}

android {
    namespace = "com.viskit.user"
}

dependencies {
    implementation(projects.data.user)
}
