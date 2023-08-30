plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("com.squareup.sqldelight")
    id("io.realm.kotlin") version "1.10.0"
    id("dev.icerock.mobile.multiplatform-resources")
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
            export("dev.icerock.moko:resources:0.22.3")
            export("dev.icerock.moko:graphics:0.9.0")
        }
    }

    sourceSets {
        val coroutineVersion = "1.7.2"
        val retrofitCoroutineAdapterVersion = "0.9.2"
        val retrofitVersion = "2.9.0"
        val okHttpVersion = "4.11.0"
        val moshiVersion = "1.13.0"
        val lifecycleViewModelVersion = "2.6.1"
        val koinCoreVersion = "3.4.2"
        val koinAndroidVersion = "3.4.2"
        val koinComposeVersion = "3.4.5"

        val commonMain by getting {
            dependencies {
                implementation("io.insert-koin:koin-core:$koinCoreVersion")
                implementation("io.insert-koin:koin-androidx-compose:$koinComposeVersion")
                implementation("io.insert-koin:koin-android:$koinAndroidVersion")

                // Java Compatibility
                implementation("io.insert-koin:koin-android-compat:$koinAndroidVersion")
                // Jetpack WorkManager
                implementation("io.insert-koin:koin-androidx-workmanager:$koinAndroidVersion")
//                // Navigation Graph
//                implementation("io.insert-koin:koin-androidx-navigation:$koinAndroidVersion")

                // Coroutines
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutineVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutineVersion")
                implementation("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:$retrofitCoroutineAdapterVersion")

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

                implementation("io.ktor:ktor-client-android:2.3.0")
                // OpenAI Client
                api("com.aallam.openai:openai-client:3.2.3")

//                api("com.arkivanov.essenty:parcelable:0.1.3")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")

                // SQLDelight
                implementation("com.squareup.sqldelight:runtime:1.5.5")
                implementation("com.squareup.sqldelight:coroutines-extensions:1.5.5")

                // Realm
                api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.2") // Add to use coroutines with the SDK
                api("io.realm.kotlin:library-base:1.10.0") // Add to only use the local database
                api("io.realm.kotlin:library-sync:1.10.0") // Add to use Device Sync

                // Moco
                api("dev.icerock.moko:resources:0.22.3")

                //
                implementation("co.touchlab:stately-concurrency:2.0.0-rc3")
                // implementation("co.touchlab:stately-iso-collections:2.0.0-rc3")

                // Napier
                api("io.github.aakira:napier:2.6.1")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))

                api("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.2")
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("androidx.core:core-ktx:1.10.1")
                implementation("com.google.android.material:material:1.9.0")

                // ViewModel & LiveData
                implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleViewModelVersion")
                implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleViewModelVersion")
                implementation("androidx.activity:activity-compose:1.8.0-alpha06")

                // Coroutines
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutineVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutineVersion")
                implementation("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:$retrofitCoroutineAdapterVersion")

                // SQLDelight
                implementation("com.squareup.sqldelight:android-driver:1.5.5")

                // Room
//                implementation("androidx.room:room-runtime:$roomVersion")
//                implementation("androidx.room:room-ktx:$roomVersion")
//                implementation("androidx.room:room-paging:$roomPagingVersion")
//
//                configurations.getByName("kapt").dependencies.add(
//                    org.gradle.api.internal.artifacts.dependencies.DefaultExternalModuleDependency(
//                        "androidx.room",
//                        "room-compiler",
//                        "2.5.1"
//                    )
//                )

                implementation("androidx.datastore:datastore-preferences:1.0.0")

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
        val androidUnitTest by getting {
            dependencies {
                implementation("junit:junit:4.13.2")
                implementation("androidx.test:core:1.5.0")
                implementation("androidx.test:rules:1.5.0")
                implementation("androidx.test:runner:1.5.2")
                implementation("app.cash.turbine:turbine:0.12.1")
                implementation("androidx.test.espresso:espresso-core:3.5.1")
                implementation("com.google.truth:truth:1.1.2")
            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependencies {
                implementation("com.squareup.sqldelight:native-driver:1.5.5")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-native:1.3.8")

            }

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
    compileSdk = 34
    defaultConfig {
        minSdk = 24
//        targetSdk = 34
    }
}

sqldelight {
    database("EngAppDatabase") {
//        packageName = "com.san.englishbender.data.local"
        packageName = "com.san.englishbender.database"
        sourceFolders = listOf("sqldelight")
    }
}

multiplatformResources {
    multiplatformResourcesPackage = "com.san.englishbender"
    multiplatformResourcesClassName = "SharedRes"
}