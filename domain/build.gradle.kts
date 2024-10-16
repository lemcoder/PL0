plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

kotlin {
    jvmToolchain(17)
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

dependencies {
    api(libs.coroutines.core)
    runtimeOnly(libs.coroutines.android)

    // Panda Loop SDK
    implementation(libs.mikroaudio.core)
    implementation(libs.mikrosoundfont.lib)

    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.kotlin.atomicfu)

    testImplementation(libs.kotlin.test)
    testImplementation(libs.coroutines.test)
}