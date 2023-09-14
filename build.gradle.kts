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

tasks.register("changeFilePermissions") {
    group = "Custom"
    description = "Change file permissions"

    doLast {
        val command = mutableListOf<String>()
        command.add("adb")
        command.add("shell")
        command.add("chmod")
        command.add("777")
        command.add("/data/data/com.san.englishbender.android/files/default.realm")

        val process = ProcessBuilder(*command.toTypedArray())
            .directory(project.rootDir)
            .redirectOutput(ProcessBuilder.Redirect.INHERIT)
            .redirectError(ProcessBuilder.Redirect.INHERIT)
            .start()

        process.waitFor()
    }
}

tasks.register("pullRealmDbToSDCard", Exec::class) {
    group = "Custom"
    description = "Custom file operations"

    val fileName = "default.realm"

    commandLine(
        "adb",
        "shell",
        "run-as",
        "com.san.englishbender.android",
        "cat",
        "/data/data/com.san.englishbender.android/files/$fileName",
        ">",
        "/sdcard/Realm/$fileName"
    )

    doLast {
        val pullCommand = mutableListOf<String>()
        pullCommand.add("adb")
        pullCommand.add("pull")
        pullCommand.add("/sdcard/Realm/$fileName")

//        val removeCommand = mutableListOf<String>()
//        removeCommand.add("adb")
//        removeCommand.add("shell")
//        removeCommand.add("rm")
//        removeCommand.add("/sdcard/Realm/$fileName")

        val pullProcess = ProcessBuilder(*pullCommand.toTypedArray())
            .directory(project.rootDir)
            .redirectOutput(ProcessBuilder.Redirect.INHERIT)
            .redirectError(ProcessBuilder.Redirect.INHERIT)
            .start()

        pullProcess.waitFor()

//        val removeProcess = ProcessBuilder(*removeCommand.toTypedArray())
//            .directory(project.rootDir)
//            .redirectOutput(ProcessBuilder.Redirect.INHERIT)
//            .redirectError(ProcessBuilder.Redirect.INHERIT)
//            .start()
//
//        removeProcess.waitFor()
    }
}

tasks.register("pullRealmDbFromSDCardToPC") {
    group = "Custom"
    description = "Pull files from the device using adb"

    doLast {
        val command = mutableListOf<String>()
        command.add("adb")
        command.add("pull")
        command.add("/sdcard/Realm/default.realm")
        command.add("C:\\Users\\alsudnik\\Workspace\\RealmDB")

        val process = ProcessBuilder(*command.toTypedArray())
            .directory(project.rootDir)
            .redirectOutput(ProcessBuilder.Redirect.INHERIT)
            .redirectError(ProcessBuilder.Redirect.INHERIT)
            .start()

        process.waitFor()
    }
}

tasks.register("exportRealmDb") {
    group = "Custom"
    description = "Custom combined task"

    dependsOn("pullRealmDbToSDCard")
    dependsOn("pullRealmDbFromSDCardToPC")

    doLast {
        println("pullRealmDbToSDCard and pullRealmDbFromSDCardToPC tasks completed.")
    }
}