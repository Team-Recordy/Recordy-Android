plugins {
    alias(libs.plugins.recordy.remote)
}

android {
    namespace = "com.record.video"
}

dependencies {
    implementation(projects.data.video)
}
