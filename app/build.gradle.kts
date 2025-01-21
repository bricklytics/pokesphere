plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.dagger.ksp)
}

android {
    namespace = "com.bricklytics.pokesphere.uilayer"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.bricklytics.pokesphere.uilayer"
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

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlin.get()
    }

//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_17
//        targetCompatibility = JavaVersion.VERSION_17
//    }
}

dependencies {
    implementation(project(":domainlayer"))
    implementation(project("::datalayer"))

    // Core Android dependencies
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.lifecycle)
    implementation(libs.androidx.startup)
    implementation(libs.androidx.palette)
    implementation(libs.fragment.kotlin)
    implementation(libs.fragment.compose)

    // Lifecycle components
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // Compose dependencies
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Coroutine support
    implementation(libs.coroutines.core)

    // Glide image loading
    implementation(libs.glide)

    // Timber for logging
    implementation(libs.timber)

    // Dagger Hilt
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.hilt.plugin)
    androidTestImplementation(libs.hilt.testing)
    ksp(libs.hilt.compiler)
    kspAndroidTest(libs.hilt.compiler)

    // Unit testing
    testImplementation(libs.junit)
    testImplementation(libs.androidx.test.core)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.coroutines.test)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.android.test.runner)
}