//package com.san.englishbender.core
//
//import org.gradle.tooling.GradleConnector
//import org.gradle.tooling.ProjectConnection
//
//fun executeGradleTask() {
//    val projectDir = File("/path/to/your/project") // Замените на путь к вашему проекту
//
//    val connection: ProjectConnection = GradleConnector.newConnector()
//        .forProjectDirectory(projectDir)
//        .connect()
//
//    try {
//        val buildResult = connection.newBuild()
//            .forTasks("clean") // Замените на имя вашей Gradle-задачи
//            .setStandardOutput(System.out) // Вывод стандартного вывода
//            .setStandardError(System.err) // Вывод стандартной ошибки
//            .run()
//
//        if (buildResult.getFailure() == null) {
//            println("Gradle task executed successfully.")
//        } else {
//            println("Gradle task execution failed.")
//        }
//    } finally {
//        connection.close()
//    }
//}