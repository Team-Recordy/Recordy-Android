plugins {
    alias(libs.plugins.recordy.local)
}

android {
    namespace = "com.record.video"
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.data.video)
}
