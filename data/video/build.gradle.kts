plugins {
    alias(libs.plugins.recordy.data)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.record.video"
}

dependencies {
    implementation(projects.domain.video)
    implementation(projects.domain.upload)
}
