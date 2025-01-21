plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.dagger.ksp)
}

android {
    namespace = "com.bricklytics.pokesphere.domainlayer"
    compileSdk = 35

    defaultConfig {
        minSdk = 28
    }
}

dependencies {

    // Core Android dependencies
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    // Unit testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    testImplementation(libs.androidx.test.core)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
    androidTestImplementation(libs.androidx.espresso.core)

    //Timber for logging
    implementation(libs.timber)

    // Dagger Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
}