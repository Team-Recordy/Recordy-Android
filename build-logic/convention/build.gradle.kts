plugins {
    `kotlin-dsl`
}

group = "org.record.convention"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.ksp.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "recordy.android.application"
            implementationClass = "AndroidApplicationPlugin"
        }

        register("androidLibrary") {
            id = "recordy.android.library"
            implementationClass = "AndroidLibraryPlugin"
        }

        register("androidHilt") {
            id = "recordy.android.hilt"
            implementationClass = "HiltPlugin"
        }

        register("javaLibrary") {
            id = "recordy.java.library"
            implementationClass = "JavaLibraryPlugin"
        }

        register("AndroidTest") {
            id = "recordy.plugin.android.test"
            implementationClass = "AndroidTestPlugin"
        }

        register("UnitTest") {
            id = "recordy.plugin.test"
            implementationClass = "UnitTestPlugin"
        }
    }
}