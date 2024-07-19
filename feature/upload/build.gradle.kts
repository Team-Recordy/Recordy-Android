plugins {
    alias(libs.plugins.recordy.feature)
}

android {
    namespace = "com.record.upload"
}

dependencies {
    implementation(libs.bundles.aws)
    implementation(libs.bundles.accompanist)
    implementation(libs.lightcompressor)
    implementation(projects.domain.upload)
    implementation(projects.domain.keyword)
    implementation(projects.core.common)
}
