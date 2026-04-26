plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("org.jetbrains.kotlin.kapt")
    id("com.google.gms.google-services")
    kotlin("plugin.serialization")

}

android {
    namespace = "com.example.intellecta"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.intellecta"
        minSdk = 28
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }

    lint {
        disable.add("NullSafeMutableLiveData")
        checkOnly.add("ExtraTranslation")
    }
}

configurations.all {
    resolutionStrategy {
        force("io.ktor:ktor-client-core:2.3.7")
        force("io.ktor:ktor-client-okhttp:2.3.7")
        force("io.ktor:ktor-client-content-negotiation:2.3.7")
        force("io.ktor:ktor-serialization-kotlinx-json:2.3.7")
        force("io.ktor:ktor-client-logging:2.3.7")
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

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")

    implementation("androidx.navigation:navigation-compose:2.9.3")

    // ROOM
    implementation("androidx.room:room-runtime:2.7.2")
    implementation("androidx.room:room-ktx:2.7.2")
    kapt("androidx.room:room-compiler:2.7.2")

    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // Koin
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)
    implementation(libs.koin.androidx.workmanager)


    // KTOR - DEFINE FIRST WITH EXPLICIT VERSIONS
    implementation("io.ktor:ktor-client-core:2.3.7")
    implementation("io.ktor:ktor-client-okhttp:2.3.7")
    implementation("io.ktor:ktor-client-content-negotiation:2.3.7")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.7")
    implementation("io.ktor:ktor-client-logging:2.3.7")

    // Supabase with BOM - MUST COME AFTER KTOR
    implementation(platform("io.github.jan-tennert.supabase:bom:2.6.1"))
    implementation("io.github.jan-tennert.supabase:storage-kt")
    implementation("io.github.jan-tennert.supabase:postgrest-kt")   // ← NEW: DB queries
    implementation("io.github.jan-tennert.supabase:gotrue-kt")      // ← NEW: Auth
    implementation("io.github.jan-tennert.supabase:functions-kt")

    // Secure Storage
    implementation("androidx.security:security-crypto:1.1.0-alpha06")


//    // Retrofit for API calls
//    implementation("com.squareup.retrofit2:retrofit:2.9.0")
//    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
//    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // WorkManager
    implementation("androidx.work:work-runtime-ktx:2.9.0")

    // Firebase (analytics only, auth removed)
    implementation(platform("com.google.firebase:firebase-bom:33.1.0"))
    implementation("com.google.firebase:firebase-analytics")

    // Test dependencies
    androidTestImplementation("io.ktor:ktor-client-core:2.3.7")
    androidTestImplementation("io.ktor:ktor-client-okhttp:2.3.7")
    androidTestImplementation("io.ktor:ktor-client-content-negotiation:2.3.7")
    androidTestImplementation("io.ktor:ktor-serialization-kotlinx-json:2.3.7")
    androidTestImplementation("io.ktor:ktor-client-logging:2.3.7")

}