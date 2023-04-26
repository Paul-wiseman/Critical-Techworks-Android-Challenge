plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs")
    id("kotlin-parcelize")
}

android {
    namespace = "com.example.criticaltechworkschallenge"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.criticaltechworkschallenge"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }


    buildTypes {
        getByName("release") {
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
        viewBinding = true
    }

    flavorDimensions.add("default")
    productFlavors {
        create("cnn") {
            buildConfigField("String", "sources", "\"cnn\"")
            versionNameSuffix = "-dev"
            applicationIdSuffix = ".dev"
        }
        create("bbcnews") {
            buildConfigField("String", "sources", "\"bbc-news\"")
            versionNameSuffix = "-dev"
            applicationIdSuffix = ".dev"
        }
        create("foxnews") {
            buildConfigField("String", "sources", "\"fox-news\"")
            versionNameSuffix = "-dev"
            applicationIdSuffix = ".dev"
        }
    }

    testOptions {
        unitTests {
            // Enable support for running unit tests on Android devices
            isIncludeAndroidResources = true

            // Add this line to mock the Looper class
        }
    }

}

dependencies {

    implementation("androidx.core:core-ktx:1.10.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.test.ext:junit-ktx:1.1.5")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.3")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Mockk.io
    androidTestImplementation("io.mockk:mockk-android:1.13.4")
    testImplementation("io.mockk:mockk:1.13.4")

    //Testing
    testImplementation("com.squareup.okhttp3:mockwebserver:5.0.0-alpha.10")
    testImplementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.3")
    testImplementation ("androidx.arch.core:core-testing:2.2.0")
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.2")
    // AndroidX Test - JVM testing
    testImplementation ("androidx.test.ext:junit-ktx:1.1.5")

    testImplementation ("androidx.test:core-ktx:1.5.0")

    testImplementation ("org.robolectric:robolectric:4.9")


    //Hilt Dependency Injection
    implementation("com.google.dagger:hilt-android:2.45")
    kapt("com.google.dagger:hilt-compiler:2.45")

    // Navigation component
    implementation("androidx.navigation:navigation-fragment-ktx:2.6.0-alpha09")
    implementation("androidx.navigation:navigation-ui-ktx:2.6.0-alpha09")

    // Image Loading library Coil
    implementation("io.coil-kt:coil:2.2.2")

    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")

    //Room
    implementation("androidx.room:room-runtime:2.2.5")
    implementation("androidx.room:room-ktx:2.2.5")
    kapt("androidx.room:room-compiler:2.2.5")

    // biometric auth
    implementation("androidx.biometric:biometric:1.1.0")

}
