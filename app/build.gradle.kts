plugins {
    alias(libs.plugins.recordy.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.record.recordy"

    defaultConfig {
        applicationId = "com.record.recordy"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.designsystem)
    implementation(projects.core.buildConfig)
    implementation(projects.core.datastore)
    implementation(projects.core.network)
}