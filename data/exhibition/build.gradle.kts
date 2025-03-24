plugins {
    alias(libs.plugins.recordy.data)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.viskit.exhibition"
}

dependencies {
    implementation(projects.domain.exhibition)
    implementation(projects.domain.video)
}
