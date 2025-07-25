import org.jetbrains.kotlin.compose.compiler.gradle.ComposeFeatureFlag

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "pl.lemanski.pandaloop"
    compileSdk = 34

    defaultConfig {
        applicationId = "pl.lemanski.pandaloop"
        minSdk = 24
        targetSdk = 35
        versionCode = 2
        versionName = "0.0.1"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures {
        compose = true
    }

    composeCompiler {
        featureFlags.add(ComposeFeatureFlag.StrongSkipping)
        featureFlags.add(ComposeFeatureFlag.OptimizeNonSkippingGroups)
        stabilityConfigurationFile = file("compose-stability")
    }
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(projects.domain)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.material3)
    debugImplementation(libs.androidx.ui.tooling)
    implementation(libs.androidx.ui.preview)
}