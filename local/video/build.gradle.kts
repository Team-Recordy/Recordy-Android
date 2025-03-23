plugins {
    alias(libs.plugins.recordy.local)
}

android {
    namespace = "com.viskit.video"
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.data.video)
}
