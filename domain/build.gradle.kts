plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "pl.lemanski.pandaloop.domain"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
    }

    testOptions {
        unitTests {
            isReturnDefaultValues = true
        }
    }
}

kotlin {
    jvmToolchain(17)

    androidTarget()

    sourceSets {
        commonMain.dependencies {
            api(libs.coroutines.core)
            api(libs.kotlin.serialization)

            implementation(libs.mikroaudio.core)
            implementation(libs.mikrosoundfont.lib)

            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.kotlin.atomicfu)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.coroutines.test)
        }

        androidMain.dependencies {
            runtimeOnly(libs.coroutines.android)
        }
    }
}