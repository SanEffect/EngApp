package com.san.englishbender.android.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable


@Composable
fun EnglishBenderTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
//    val colors = if (darkTheme) {
//        darkColors(
//            primary = Color(0xFFBB86FC),
//            primaryVariant = Color(0xFF3700B3),
//            secondary = Color(0xFF03DAC5)
//        )
//    } else {
//        lightColors(
//            primary = Color(0xFF6200EE),
//            primaryVariant = Color(0xFF3700B3),
//            secondary = Color(0xFF03DAC5)
//        )
//    }

    val colors = if (darkTheme) darkReplyBlueColors else LightColorsMain
//    val colors = if (darkTheme) darkReplyBlueColors else lightPinkColors

    MaterialTheme(
        colorScheme = colors,
        shapes = EngAppShapes,
        typography = EngAppTypography,
        content = content
    )
}
