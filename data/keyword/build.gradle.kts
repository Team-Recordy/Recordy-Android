plugins {
    alias(libs.plugins.recordy.data)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.record.keyword"
}

dependencies {
    implementation(projects.domain.keyword)
}
