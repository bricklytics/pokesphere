plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.dagger.ksp)
}

android {
    namespace = "com.bricklytics.pokesphere.datalayer"
    compileSdk = 35
    buildFeatures.buildConfig = true

    defaultConfig {
        minSdk = 28
    }

    buildTypes {
        debug {
            buildConfigField("String", "BASE_URL", "\"https://pokeapi.co/\"")
        }
    }

}

dependencies {
    implementation(project(":domainlayer"))

    // Coroutine support
    implementation(libs.coroutines)
    testImplementation(libs.coroutines)
    testImplementation(libs.coroutines.test)

    // Core Android dependencies
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    // ROOM Database
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    // Unit testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    testImplementation(libs.androidx.test.core)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
    androidTestImplementation(libs.androidx.espresso.core)
    testImplementation(libs.robolectric)

    // Networking - Retrofit and OkHttp
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.gson)
    implementation(platform(libs.okhttp.bom))
    implementation(libs.okhttp.interceptor)
    implementation(libs.okhttp.core)
    testImplementation(libs.okhttp.mockwebserver)
    testImplementation(libs.androidx.arch.core)

    // Ktor
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.android)
    implementation(libs.ktor.client.serialization)
    implementation(libs.ktor.serialization.json)
    implementation(libs.ktor.logging)
    implementation(libs.ktor.cio)
    implementation(libs.ktor.okhttp)

    // Timber for logging
    implementation(libs.timber)

    // Dagger Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
}