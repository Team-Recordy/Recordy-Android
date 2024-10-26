plugins {
    alias(libs.plugins.recordy.feature)
}

android {
    namespace = "com.record.search"
}

dependencies {
    implementation(projects.domain.exhibition)
}
