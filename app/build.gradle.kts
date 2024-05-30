import org.jetbrains.kotlin.config.LanguageVersion

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.compose.compiler)
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
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures {
        compose = true
    }

    composeCompiler {
        enableStrongSkippingMode = true
        enableNonSkippingGroupOptimization = true
        stabilityConfigurationFile = file("compose-stability")
    }
}

dependencies {
    implementation(projects.domain)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    implementation(libs.navigation.compose)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.material3)
    debugImplementation(libs.androidx.ui.tooling)
    implementation(libs.androidx.ui.preview)
}