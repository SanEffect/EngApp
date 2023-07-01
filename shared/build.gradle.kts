plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("kotlin-parcelize")
    id("kotlin-kapt")
//    id("com.google.devtools.ksp") version "1.8.10-1.0.9"
}

kotlin {
    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
        }
    }

    sourceSets {

        val composeVersion = "1.4.0"
        val material3Version = "1.1.0"

        val voyagerVersion = "1.0.0-rc04"

        val coroutineVersion = "1.5.0"
        val retrofitCoroutineAdapterVersion = "0.9.2"

        val roomVersion = "2.6.0-alpha01"
        val roomPagingVersion = "2.6.0-alpha01"
        val retrofitVersion = "2.9.0"
        val okHttpVersion = "4.9.2"
        val moshiVersion = "1.13.0"
        val pagingRuntimeVersion = "3.1.1"
        val pagingComposeVersion = "1.0.0-alpha18"

        val lifecycleViewModelVersion = "2.5.1"

        val hiltVersion = "2.44"
        val koinCoreVersion = "3.4.0"
        val koinAndroidVersion = "3.4.0"
        val koinComposeVersion = "3.4.3"

        val timberVersion = "5.0.1"

        val commonMain by getting {
            dependencies {

                // --- Compose ---
//                implementation("androidx.compose.ui:ui:$composeVersion")
                // Integration with activities
                implementation("androidx.activity:activity-compose:1.7.0")
                // Integration with ViewModels
                implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")
                // Tooling support (Previews, etc.)
                implementation("androidx.compose.ui:ui-tooling:$composeVersion")
                // Foundation (Border, Background, Box, Image, Scroll, shapes, animations, etc.)
                implementation("androidx.compose.foundation:foundation:$composeVersion")

                // Material Design
                implementation("androidx.compose.material:material:$composeVersion")
                api("androidx.compose.material3:material3:$material3Version")
                api("androidx.compose.material3:material3-window-size-class:$material3Version")
                // Material design icons
                api("androidx.compose.material:material-icons-core:$composeVersion")
                api("androidx.compose.material:material-icons-extended:$composeVersion")

                implementation("com.google.accompanist:accompanist-insets:0.31.0-alpha")

                // BottomSheetNavigator
//                api("cafe.adriel.voyager:voyager-bottom-sheet-navigator:$voyagerVersion")
//                // TabNavigator
//                api("cafe.adriel.voyager:voyager-tab-navigator:$voyagerVersion")
//                // Transitions
//                api("cafe.adriel.voyager:voyager-transitions:$voyagerVersion")
//                // Android ViewModel integration
//                api("cafe.adriel.voyager:voyager-androidx:$voyagerVersion")
//                // Hilt integration
//                api("cafe.adriel.voyager:voyager-hilt:$voyagerVersion")
//                // LiveData integration
//                api("cafe.adriel.voyager:voyager-livedata:$voyagerVersion")
//
//                // Voyager Navigation library
//                api("cafe.adriel.voyager:voyager-navigator:$voyagerVersion")

                //ViewModel & LiveData
                implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleViewModelVersion")
                implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleViewModelVersion")

                implementation("io.insert-koin:koin-core:$koinCoreVersion")
                implementation("io.insert-koin:koin-androidx-compose:$koinComposeVersion")
                implementation("io.insert-koin:koin-android:$koinAndroidVersion")

                // Java Compatibility
                implementation("io.insert-koin:koin-android-compat:$koinAndroidVersion")
                // Jetpack WorkManager
                implementation("io.insert-koin:koin-androidx-workmanager:$koinAndroidVersion")
                // Navigation Graph
                implementation("io.insert-koin:koin-androidx-navigation:$koinAndroidVersion")

                // Timber
                implementation("com.jakewharton.timber:timber:$timberVersion")

                // Coroutines
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutineVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutineVersion")
                implementation("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:$retrofitCoroutineAdapterVersion")

                // Paging
                api("androidx.paging:paging-runtime-ktx:$pagingRuntimeVersion")
                api("androidx.paging:paging-compose:$pagingComposeVersion")
                api("androidx.paging:paging-common-ktx:3.1.1")

                // Room
                api("androidx.room:room-runtime:$roomVersion")
                api("androidx.room:room-ktx:$roomVersion")
                api("androidx.room:room-paging:$roomPagingVersion")

//                configurations.getByName("kapt").dependencies.add(
//                    org.gradle.api.internal.artifacts.dependencies.DefaultExternalModuleDependency(
//                        "androidx.room",
//                        "room-compiler",
//                        "2.5.0-alpha02"
////                        roomVersion
//                    )
//                )

                // Network
                implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
                implementation("com.squareup.retrofit2:converter-scalars:$retrofitVersion")
                implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")
                implementation("com.squareup.retrofit2:adapter-rxjava2:$retrofitVersion")
                implementation("com.squareup.retrofit2:converter-moshi:$retrofitVersion")
                implementation("com.squareup.okhttp3:okhttp:$okHttpVersion")
                implementation("com.squareup.okhttp3:logging-interceptor:$okHttpVersion")

                implementation("com.squareup.moshi:moshi:$moshiVersion")
                implementation("com.squareup.moshi:moshi-kotlin:$moshiVersion")

//                api("com.arkivanov.essenty:parcelable:0.1.3")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")

                implementation("io.ktor:ktor-client-android:2.3.0")

                implementation("com.aallam.openai:openai-client:3.2.3")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {

                implementation("androidx.core:core-ktx:1.10.0")
                implementation("com.google.android.material:material:1.8.0")

                // --- Compose ---
                implementation("androidx.compose.ui:ui:$composeVersion")
                // Integration with activities
                implementation("androidx.activity:activity-compose:1.7.0")
                // Integration with ViewModels
                implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")
                // Tooling support (Previews, etc.)
                implementation("androidx.compose.ui:ui-tooling:$composeVersion")
                // Foundation (Border, Background, Box, Image, Scroll, shapes, animations, etc.)
                implementation("androidx.compose.foundation:foundation:$composeVersion")


//                // Material Design
//                implementation("androidx.compose.material:material:$composeVersion")
//                implementation("androidx.compose.material3:material3:$material3Version")
//                implementation("androidx.compose.material3:material3-window-size-class:$material3Version")
//                // Material design icons
//                implementation("androidx.compose.material:material-icons-core:$composeVersion")
//                implementation("androidx.compose.material:material-icons-extended:$composeVersion")

                // Accompanist
//                implementation("com.google.accompanist:accompanist-insets:0.23.1")

                //ViewModel & LiveData
                implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleViewModelVersion")
                implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleViewModelVersion")

                implementation("androidx.activity:activity-compose:1.8.0-alpha02")

                // Timber
                implementation("com.jakewharton.timber:timber:$timberVersion")

                // Coroutines
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutineVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutineVersion")
                implementation("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:$retrofitCoroutineAdapterVersion")

                // Paging
                implementation("androidx.paging:paging-runtime-ktx:$pagingRuntimeVersion")
                implementation("androidx.paging:paging-compose:$pagingComposeVersion")
//                implementation("androidx.paging:paging-common-ktx:3.1.1")

                // Room
                implementation("androidx.room:room-runtime:$roomVersion")
                implementation("androidx.room:room-ktx:$roomVersion")
                implementation("androidx.room:room-paging:$roomPagingVersion")

                configurations.getByName("kapt").dependencies.add(
                    org.gradle.api.internal.artifacts.dependencies.DefaultExternalModuleDependency(
                        "androidx.room",
                        "room-compiler",
                        "2.5.1"
                    )
                )

                // Network
                implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
                implementation("com.squareup.retrofit2:converter-scalars:$retrofitVersion")
                implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")
                implementation("com.squareup.retrofit2:adapter-rxjava2:$retrofitVersion")
                implementation("com.squareup.retrofit2:converter-moshi:$retrofitVersion")
                implementation("com.squareup.okhttp3:okhttp:$okHttpVersion")
                implementation("com.squareup.okhttp3:logging-interceptor:$okHttpVersion")

                implementation("com.squareup.moshi:moshi:$moshiVersion")
                implementation("com.squareup.moshi:moshi-kotlin:$moshiVersion")
            }
        }
        val androidUnitTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    namespace = "com.san.englishbender"
    compileSdk = 33
    defaultConfig {
        minSdk = 24
        targetSdk = 33
    }
}
dependencies {
//    annotationProcessor("androidx.room:room-compiler:2.6.0-alpha01")

//    kapt("androidx.room:room-compiler:2.5.0-alpha02")

//    ksp("io.github.raamcosta.compose-destinations:ksp:1.4.4-beta")
}
