plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

kotlin {
    jvmToolchain(17)

    sourceSets.all {
        languageSettings {
            languageVersion = "2.0"
        }
    }
}

android {
    namespace = "pl.lemanski.pandaloop"
    compileSdk = 34

    defaultConfig {
        applicationId = "pl.lemanski.pandaloop"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.11-dev-k2.0.0-Beta5-b5a216d0ac6"
    }
}

dependencies {
    implementation(libs.pandaloop.core)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.material3)
}