plugins {
    `kotlin-dsl`
}

group = "org.viskit.convention"

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
            id = "viskit.android.application"
            implementationClass = "AndroidApplicationPlugin"
        }

        register("androidLibrary") {
            id = "viskit.android.library"
            implementationClass = "AndroidLibraryPlugin"
        }

        register("androidComposeLibrary") {
            id = "viskit.android.compose.library"
            implementationClass = "AndroidComposeLibraryPlugin"
        }

        register("androidHilt") {
            id = "viskit.android.hilt"
            implementationClass = "HiltPlugin"
        }

        register("javaLibrary") {
            id = "viskit.java.library"
            implementationClass = "JavaLibraryPlugin"
        }

        register("buildConfig") {
            id = "viskit.plugin.build.config"
            implementationClass = "BuildConfigPlugin"
        }

        register("androidTest") {
            id = "viskit.plugin.android.test"
            implementationClass = "AndroidTestPlugin"
        }

        register("unitTest") {
            id = "viskit.plugin.test"
            implementationClass = "UnitTestPlugin"
        }

        register("recordyFeature") {
            id = "viskit.feature"
            implementationClass = "RecordyFeaturePlugin"
        }

        register("recordyData") {
            id = "viskit.data"
            implementationClass = "RecordyDataPlugin"
        }

        register("recordyLocal") {
            id = "viskit.local"
            implementationClass = "RecordyLocalPlugin"
        }

        register("recordyRemote") {
            id = "viskit.remote"
            implementationClass = "RecordyRemotePlugin"
        }
    }
}
