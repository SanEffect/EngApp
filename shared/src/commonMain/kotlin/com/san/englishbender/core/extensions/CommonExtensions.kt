package com.san.englishbender.core.extensions

import com.san.englishbender.core.AppConstants.RECORD_MAX_LENGTH_TITLE

inline fun <reified T : Any> Any.cast(): T {
    return this as T
}

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

val Any?.isNull get() = this == null

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