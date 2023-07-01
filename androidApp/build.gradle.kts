plugins {
    id("com.android.application")
    kotlin("android")
    id("com.google.devtools.ksp") version "1.8.10-1.0.9"
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
        kotlinCompilerExtensionVersion = "1.4.0"
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

    applicationVariants.all {
        addJavaSourceFoldersToModel(
            File(buildDir, "generated/ksp/$name/kotlin")
        )
    }
}

val pagingRuntimeVersion = "3.1.1"
val pagingComposeVersion = "1.0.0-alpha18"

val koinCoreVersion = "3.4.0"
val koinAndroidVersion = "3.4.0"
val koinComposeVersion = "3.4.3"

dependencies {
    implementation(project(":shared"))

    implementation("io.insert-koin:koin-core:3.4.0")
    implementation("io.insert-koin:koin-android:$koinAndroidVersion")
    implementation("io.insert-koin:koin-androidx-compose:$koinComposeVersion")

    implementation("androidx.navigation:navigation-compose:2.6.0-beta01")

    // Navigation
    implementation("androidx.navigation:navigation-compose:2.5.3")
    implementation("io.github.raamcosta.compose-destinations:core:1.4.4-beta")
    ksp("io.github.raamcosta.compose-destinations:ksp:1.4.4-beta")
    implementation("io.github.raamcosta.compose-destinations:animations-core:1.4.4-beta")

    // Timber
    implementation("com.jakewharton.timber:timber:5.0.1")

    implementation("androidx.compose.ui:ui:1.3.1")
    implementation("androidx.compose.ui:ui-tooling:1.3.1")
    implementation("androidx.compose.ui:ui-tooling-preview:1.3.1")
    implementation("androidx.compose.foundation:foundation:1.3.1")
    implementation("androidx.compose.material:material:1.3.1")
    implementation("androidx.activity:activity-compose:1.6.1")
}