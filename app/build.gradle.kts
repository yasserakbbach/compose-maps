import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("kapt")
    alias(libs.plugins.hilt.plugin)
    id("kotlinx-serialization")
}

android {
    namespace = "com.yasserakbbach.composemaps"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.yasserakbbach.composemaps"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Locate and load apikeys.properties
        val mapsKeyFile = project.rootProject.file("apikeys.properties")
        val properties = Properties()
        properties.load(mapsKeyFile.inputStream())

        // Fetch the map key
        val googleMapsApiKey = properties.getProperty("GOOGLE_MAPS_API_KEY") ?: ""

        // Inject the key dynamically into the manifest
        manifestPlaceholders["GOOGLE_MAPS_API_KEY"] = googleMapsApiKey
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
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.kapt.hilt.compiler)

    // Hilt Navigation Compose
    implementation(libs.hilt.navigation.compose)

    // Navigation Compose
    implementation(libs.navigation.compose)

    // Coroutines
    implementation(libs.coroutines)

    // Splash API
    implementation(libs.splash)

    // Kotlin Serialization
    implementation(libs.kotlin.serialization)

    // Play Services Location
    implementation(libs.play.services.location)

    // Maps Compose
    implementation(libs.maps.compose)

    // Data Store
    implementation(libs.data.store.preferences)
}

kapt {
    correctErrorTypes = true
}