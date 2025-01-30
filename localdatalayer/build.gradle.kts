plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.dagger.ksp)
}

android {
    namespace = "com.bricklytics.pokesphere.localdatalayer"
    compileSdk = 35
    buildFeatures.buildConfig = true

    defaultConfig {
        minSdk = 28
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        ksp {
            // The schemas directory contains a schema file for each version of the Room database.
            // This is required to enable Room auto migrations.
            arg("room.schemaLocation", "$projectDir/schemas")
        }
        sourceSets.getByName("test") {
            assets.srcDir(files("$projectDir/schemas"))
        }
    }
    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {

    implementation(project(":domainlayer"))

    // Core Android dependencies
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    // Coroutine support
    implementation(libs.coroutines)
    testImplementation(libs.coroutines)
    testImplementation(libs.coroutines.test)

    //Unit Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.robolectric)

    // ROOM Database
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    testImplementation(libs.androidx.room.testing)
    ksp(libs.androidx.room.compiler)

    // Dagger Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Retrofit Gson
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.gson)
}