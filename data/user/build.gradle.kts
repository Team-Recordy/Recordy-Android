plugins {
    alias(libs.plugins.recordy.data)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.record.user"
}

dependencies {
    implementation(projects.domain.user)
}
