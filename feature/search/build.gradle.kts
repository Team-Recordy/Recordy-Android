plugins {
    alias(libs.plugins.recordy.feature)
}

android {
    namespace = "com.viskit.search"
}

dependencies {
    implementation(projects.domain.exhibition)
}
