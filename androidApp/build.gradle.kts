plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "com.san.englishbender.android"
    compileSdk = 33
    defaultConfig {
        applicationId = "com.san.englishbender.android"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
        compileSdkPreview = "UpsideDownCake"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
//        kotlinCompilerExtensionVersion = "1.4.0"
        kotlinCompilerExtensionVersion = "1.4.8"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

//    applicationVariants.all {
//        addJavaSourceFoldersToModel(
//            File(buildDir, "generated/ksp/$name/kotlin")
//        )
//    }
}

val composeVersion = "1.5.0"
val material3Version = "1.1.1"
val pagingRuntimeVersion = "3.1.1"
val pagingComposeVersion = "1.0.0-alpha18"
val koinCoreVersion = "3.4.0"
val koinAndroidVersion = "3.4.2"
val koinComposeVersion = "3.4.5"

val lifecycleViewModelVersion = "2.6.1"

dependencies {
    implementation(project(":shared"))

    implementation("io.insert-koin:koin-core:3.4.2")
    implementation("io.insert-koin:koin-android:$koinAndroidVersion")
    implementation("io.insert-koin:koin-androidx-compose:$koinComposeVersion")

    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.1")

    // --- Compose ---
    implementation("androidx.compose.ui:ui:$composeVersion")
//     Integration with activities
    implementation("androidx.activity:activity-compose:1.7.2")
    // Integration with ViewModels
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")
    // Tooling support (Previews, etc.)
    api("androidx.compose.ui:ui-tooling:$composeVersion")
    // Foundation (Border, Background, Box, Image, Scroll, shapes, animations, etc.)
    implementation("androidx.compose.foundation:foundation:$composeVersion")

//     Material Design
    implementation("androidx.compose.material:material:$composeVersion")
    api("androidx.compose.material3:material3:$material3Version")
    api("androidx.compose.material3:material3-window-size-class:$material3Version")
    // Material design icons
    api("androidx.compose.material:material-icons-core:$composeVersion")
    api("androidx.compose.material:material-icons-extended:$composeVersion")

    implementation("com.google.accompanist:accompanist-insets:0.31.0-alpha")

//    ViewModel & LiveData
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleViewModelVersion")
//    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleViewModelVersion")

    // SplashScreen
    implementation("androidx.core:core-splashscreen:1.0.1")

    // DON'T USE IT
    implementation("androidx.navigation:navigation-compose:2.7.1")

    // Color picker
    implementation("com.github.skydoves:colorpicker-compose:1.0.4")
//    implementation("com.godaddy.android.colorpicker:compose-color-picker-android:0.7.0")

    // Navigation
//    implementation("androidx.navigation:navigation-compose:2.6.0")
//    implementation("com.google.accompanist:accompanist-navigation-animation:0.23.1")


    implementation("androidx.work:work-runtime-ktx:2.8.1")



    // Timber
//    implementation("com.jakewharton.timber:timber:5.0.1")

    implementation("androidx.compose.ui:ui:1.3.1")
    implementation("androidx.compose.ui:ui-tooling:1.3.1")
    implementation("androidx.compose.ui:ui-tooling-preview:1.3.1")
    implementation("androidx.compose.foundation:foundation:1.3.1")
    implementation("androidx.compose.material:material:1.3.1")
    implementation("androidx.activity:activity-compose:1.6.1")
}