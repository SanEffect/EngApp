package com.san.englishbender

import kotlinx.datetime.LocalDateTime

interface Platform {
    val name: String
}

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