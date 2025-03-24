plugins {
    alias(libs.plugins.recordy.data)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.viskit.keyword"
}

dependencies {
    implementation(projects.domain.keyword)
}
