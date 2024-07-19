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
    compileOnly(libs.compose.compiler.extension)
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

        register("androidComposeLibrary") {
            id = "recordy.android.compose.library"
            implementationClass = "AndroidComposeLibraryPlugin"
        }

        register("androidHilt") {
            id = "recordy.android.hilt"
            implementationClass = "HiltPlugin"
        }

        register("javaLibrary") {
            id = "recordy.java.library"
            implementationClass = "JavaLibraryPlugin"
        }

        register("buildConfig") {
            id = "recordy.plugin.build.config"
            implementationClass = "BuildConfigPlugin"
        }

        register("androidTest") {
            id = "recordy.plugin.android.test"
            implementationClass = "AndroidTestPlugin"
        }

        register("unitTest") {
            id = "recordy.plugin.test"
            implementationClass = "UnitTestPlugin"
        }

        register("recordyFeature") {
            id = "recordy.feature"
            implementationClass = "RecordyFeaturePlugin"
        }

        register("recordyData") {
            id = "recordy.data"
            implementationClass = "RecordyDataPlugin"
        }

        register("recordyLocal") {
            id = "recordy.local"
            implementationClass = "RecordyLocalPlugin"
        }

        register("recordyRemote") {
            id = "recordy.remote"
            implementationClass = "RecordyRemotePlugin"
        }
    }
}
