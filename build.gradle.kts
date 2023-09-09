buildscript {
    dependencies {
        classpath("com.android.tools.build:gradle")
        classpath("dev.icerock.moko:resources-generator:0.22.3")
    }
}
plugins {
    //trick: for the same plugin versions in all sub-modules
    id("com.android.application").version("7.4.2").apply(false)
    id("com.android.library").version("7.4.2").apply(false)
    kotlin("android").version("1.8.0").apply(false)
    kotlin("multiplatform").version("1.8.22").apply(false)
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}