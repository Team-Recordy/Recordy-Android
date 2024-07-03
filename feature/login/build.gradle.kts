plugins {
    alias(libs.plugins.recordy.feature)
}

android {
    namespace = "com.record.login"
}
dependencies {
    implementation(project(":domain:oauth"))
}
