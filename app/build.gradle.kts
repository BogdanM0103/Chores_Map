plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.bogdan.choresmap"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.bogdan.choresmap"
        minSdk = 29
        targetSdk = 34
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
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

    // Google Maps Dependencies
    implementation(libs.play.services.maps)
    implementation(libs.play.services.location)
    implementation(libs.ui) // Jetpack Compose UI dependency
    implementation(libs.secrets.gradle.plugin)

    implementation("com.google.maps.android:maps-compose:2.11.3")
    implementation("com.google.android.gms:play-services-maps:18.0.2")

    // Places API
    implementation("com.google.android.libraries.places:places:3.5.0")
    implementation(platform("org.jetbrains.kotlin:kotlin-bom:2.0.0"))

    // Navigation Compose dependency
    implementation("androidx.navigation:navigation-compose:2.8.3")

    // Materal Icons dependency
    implementation("androidx.compose.material:material-icons-extended:1.4.3")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.6.4")
    implementation("androidx.compose.runtime:runtime-livedata:1.7.5")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")
}