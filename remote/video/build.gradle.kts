plugins {
    alias(libs.plugins.recordy.remote)
}

android {
    namespace = "com.viskit.video"
}

dependencies {
    implementation(projects.data.video)
    implementation(projects.core.common)
}
