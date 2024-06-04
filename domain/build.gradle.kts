import org.jetbrains.kotlin.config.LanguageVersion

plugins {
    alias(libs.plugins.androidLibrary)
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
        minSdk = 24
    }

    buildTypes {
        release {
            isMinifyEnabled = true
        }
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}

dependencies {
    api(libs.coroutines.core)
    runtimeOnly(libs.coroutines.android)

    // Panda Loop SDK
    implementation(libs.pandaloop.core)

    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.kotlin.atomicfu)

    testImplementation(libs.kotlin.test)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.mockk)
}