plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    kotlin("kapt")
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.onehungary.one"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.onehungary.one"
        minSdk = 23
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    // region: core
    implementation(libs.core.kotlin)
    implementation(libs.core.ktx)
    implementation(libs.kotlin.coroutines)
    // endregion
    // region: androidx
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    // endregion
    // region: network
    implementation(libs.okhttp)
    implementation(libs.okhttp.logger)
    implementation(libs.retrofit)
    implementation(libs.retrofitGson)
    // endregion
    // region: data
    implementation(libs.data.dataStore)
    implementation(libs.gson)
    // endregion
    // region: DI
    implementation(libs.hilt)
    kapt(libs.hilt.compiler)
    // endregion
    // region: test
    testImplementation(libs.test.junit)
    testImplementation(libs.test.mockk)
    testImplementation(libs.test.truth)
    testImplementation(libs.test.coroutines)
    testImplementation(libs.test.turbine)
    testImplementation(libs.test.okhttp.mockwebserver)
    // endregion
}