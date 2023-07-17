package com.san.englishbender

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.datetime.LocalDateTime
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

//interface Platform {
//    val name: String
//}

expect fun getPlatform(): Platform

// For Android @Parcelize
@OptIn(ExperimentalMultiplatform::class)
@OptionalExpectation
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
expect annotation class CommonParcelize()

// For Android Parcelable
expect interface CommonParcelable

// For Android @TypeParceler
@OptIn(ExperimentalMultiplatform::class)
@OptionalExpectation
@Retention(AnnotationRetention.SOURCE)
@Repeatable
@Target(AnnotationTarget.CLASS, AnnotationTarget.PROPERTY)
expect annotation class CommonTypeParceler<T, P : CommonParceler<in T>>()

// For Android Parceler
expect interface CommonParceler<T>

// For Android @TypeParceler to convert LocalDateTime to Parcel
expect object LocalDateTimeParceler: CommonParceler<LocalDateTime>

expect val dispatcherMain: CoroutineDispatcher
expect val dispatcherIO: CoroutineDispatcher
expect val dispatcherDefault: CoroutineDispatcher

expect fun randomUUID(): String

expect fun getSystemTimeInMillis(): Long

expect fun platformModule(): Module

// KMP Class Definition
expect class Platform() {
    val name: String
}