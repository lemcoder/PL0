import org.jetbrains.kotlin.config.LanguageVersion

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

kotlin {
    jvmToolchain(17)

    sourceSets.all {
        languageSettings {
            languageVersion = LanguageVersion.KOTLIN_2_0.versionString
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
        }
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompilerExt.get()
    }
}

dependencies {
    implementation(libs.pandaloop.core)
    implementation(libs.coroutines.core)
    
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    implementation(libs.navigation.compose)
    runtimeOnly(libs.coroutines.android)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.material3)
}