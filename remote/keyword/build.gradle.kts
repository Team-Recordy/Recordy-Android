plugins {
    alias(libs.plugins.recordy.remote)
}

android {
    namespace = "com.record.keyword"
}

dependencies {
    implementation(projects.data.keyword)
}
