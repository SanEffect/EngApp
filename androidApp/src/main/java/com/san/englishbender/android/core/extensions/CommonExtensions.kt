package com.san.englishbender.android.core.extensions

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.san.englishbender.core.AppConstants.RECORD_MAX_LENGTH_TITLE


fun Color.toHexCode(): String {
    val red = this.red * 255
    val green = this.green * 255
    val blue = this.blue * 255
    return String.format("#%02x%02x%02x", red.toInt(), green.toInt(), blue.toInt())
}

fun Color.toHex(): String {
    val colorInt = this.toArgb()
    return String.format("#%08X", colorInt)
}

val String.toColor
    get() = try {
        Color(android.graphics.Color.parseColor(this))
    } catch (e: Exception) {
        Color.Black
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

fun truncateText(inputText: String, maxLength: Int): String {
    if (inputText.length <= maxLength) return inputText

    var truncatedText = inputText.substring(0, maxLength)

    // We check to ensure that the last character is not part of a word
    if (!Character.isWhitespace(inputText[maxLength])) {
        val lastSpaceIndex = truncatedText.lastIndexOf(' ')
        if (lastSpaceIndex != -1) {
            truncatedText = truncatedText.substring(0, lastSpaceIndex)
        }
    }
    truncatedText += "..."

    return truncatedText
}