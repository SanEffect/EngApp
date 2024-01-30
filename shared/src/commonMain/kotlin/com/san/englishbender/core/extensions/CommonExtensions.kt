package com.san.englishbender.core.extensions

import com.san.englishbender.core.AppConstants.RECORD_MAX_LENGTH_TITLE
import com.san.englishbender.getSystemTimeInMillis
import io.github.aakira.napier.LogLevel
import io.github.aakira.napier.Napier
import io.github.aakira.napier.log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

inline fun <reified T : Any> Any.cast(): T {
    return this as T
}

inline fun <reified T> Any?.toFlow(): Flow<T> = flow {
    emit(this@toFlow as T)
}

val Any?.isNull get() = this == null
val Any?.isNotNull get() = this != null

fun Any?.ifNull(block: () -> Unit) = run {
    if (this == null) block()
}

fun String.ifEmpty(block: () -> Unit) = run {
    if(this.isEmpty()) block()
}

val String.isDigitOnly: Boolean
    get() = matches(Regex("^\\d*\$"))

val String.isAlphabeticOnly: Boolean
    get() = matches(Regex("^[a-zA-Z]*\$"))

val String.isAlphanumericOnly: Boolean
    get() = matches(Regex("^[a-zA-Z\\d]*\$"))

fun truncateTitle(str: String) : String {
    val maxLength = RECORD_MAX_LENGTH_TITLE

    if(str.isEmpty() || str.length <= maxLength) return str

    val words = str.split(" ")
    if(words.size == 1 && words[0].length > maxLength) return str.slice(0..maxLength)

    val dropped = words.dropLast(1)
    val result = dropped.joinToString(" ")

    return if(result.length > maxLength) truncateDescription(result) else result
}

fun truncateDescription(str: String) : String {
    val maxLength = RECORD_MAX_LENGTH_TITLE

    if(str.isEmpty() || str.length <= maxLength) return str

    val words = str.split(" ")
    if(words.size == 1 && words[0].length > maxLength) return str.slice(0..maxLength)

    val dropped = words.dropLast(1)
    val result = dropped.joinToString(" ")

    return if(result.length > maxLength) truncateDescription(result) else result
}

inline fun measureTimeMillis(operationName: String, block: () -> Unit): Long {
    val start = getSystemTimeInMillis()
    block()
    val timeInMillis = getSystemTimeInMillis() - start
    log(tag = "measureTimeMillis") {"---------------------------------" }
    log(tag = "measureTimeMillis") {"$operationName operation took $timeInMillis ms" }
    log(tag = "measureTimeMillis") {"---------------------------------" }
    return timeInMillis
}

fun logError(
    throwable: Throwable? = null,
    tag: String? = null,
    message: () -> String,
) {
    Napier.log(LogLevel.ERROR, tag, throwable, message())
}

//fun truncateText(inputText: String, maxLength: Int): String {
//    if (inputText.length <= maxLength) return inputText
//
//    var truncatedText = inputText.substring(0, maxLength)
//
//    // We check to ensure that the last character is not part of a word
//    if (!Character.isWhitespace(inputText[maxLength])) {
//        val lastSpaceIndex = truncatedText.lastIndexOf(' ')
//        if (lastSpaceIndex != -1) {
//            truncatedText = truncatedText.substring(0, lastSpaceIndex)
//        }
//    }
//    truncatedText += "..."
//
//    return truncatedText
//}