plugins {
    alias(libs.plugins.recordy.java.library)
}

dependencies {
    implementation(projects.core.model)
    implementation(libs.kotlinx.coroutines.core)
}
