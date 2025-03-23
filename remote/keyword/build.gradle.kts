plugins {
    alias(libs.plugins.recordy.remote)
}

android {
    namespace = "com.viskit.keyword"
}

dependencies {
    implementation(projects.data.keyword)
}
