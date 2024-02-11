package com.san.englishbender.android.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color


val Black = Color(0xFF000000)
val White = Color(0xFFFFFFFF)
val Transparent = Color(0x00000000)
val Blue = Color(0xFF252941)
val BlueDark = Color(0xFF05060B)
//val Red = Color(0xFFD13438)
val RedDark = Color(0xFF982626)
val Red = Color(0xFFB00020)

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

val ideas = Color(0xFF2196F3)
val thoughts = Color(0xFF4CAF50)
val feelings = Color(0xFFFFC107)
val selectedLabelColor = Color(0xFFFF9800)


val Green400 = Color(0xFF00C853)

val ShrinePink100 = Color(0xFFFEDBD0)
val ShrinePink50 = Color(0xFFFEEAE6)
val Brown900 = Color(0xFF442C2E)

val ReplyBlue600 = Color(0xFF4A6572)
val ReplyBlue700 = Color(0xFF344955)
val ReplyBlue800 = Color(0xFF232F34)
val ReplyOrange500 = Color(0xFFF9AA33)

val lightPinkColors = lightColorScheme(
    primary = ShrinePink100,
    onPrimary = Brown900,

    secondary = ShrinePink50,
    onSecondary = Brown900,

    background = Color.White,
    surface = Color.White,
    surfaceVariant = Brown900,
    error = Red
)

val darkReplyBlueColors = darkColorScheme(
    primary = ReplyBlue700,
//    onPrimary = ,

    secondary = ReplyOrange500,

    background = Color.White,
    surface = Color.White
)

val backgroundColors = listOf(
    Color(0xFFFFFFFF),
    Color(0xFFFFFFCC),
    Color(0xFFFFCC99),
    Color(0xFFFFCCCC),
    Color(0xFFFFCCFF),
    Color(0xFFCCCCFF),
    Color(0xFF99CCFF),
    Color(0xFFCCFFFF),
    Color(0xFF99FFCC),
    Color(0xFFCCFF99),
)

object ColorsPreset {

    val coral: Color = Color(0xFFF29131)
    val deepOrange: Color = Color(0xFFFF5722)
    val red: Color = Color(0xFFF44336)
    val pink: Color = Color(0xFFE91E63)
    val purple: Color = Color(0xFF9C27B0)
    val deepPurple: Color = Color(0xFF673AB7)
    val indigo: Color = Color(0xFF3F51B5)
    val blue: Color = Color(0xFF2196F3)
    val lightBlue: Color = Color(0xFF03A9F4)
    val cyan: Color = Color(0xFF00BCD4)
    val teal: Color = Color(0xFF009688)
    val green: Color = Color(0xFF4CAF50)
    val lightGreen: Color = Color(0xFF8BC34A)
    val lime: Color = Color(0xFFCDDC39)
    val yellow: Color = Color(0xFFFFEF35)
    val amber: Color = Color(0xFFFFC107)
    val brown: Color = Color(0xFF795548)
    val gray: Color = Color(0xFF9E9E9E)
    val white: Color = Color(0xFFFFFFFF)
    val black: Color = Color(0xFF000000)

    val values = arrayListOf(
        coral,
        deepOrange,
        red,
        pink,
        purple,
        deepPurple,
        indigo,
        blue,
        lightBlue,
        cyan,
        teal,
        green,
        lightGreen,
        lime,
        yellow,
        amber,
        brown,
        gray,
        white,
        black,
    )

//    val values = arrayListOf(
//        Color(0xFF000000),
//        Color(0xFFFFFFFF),
//        Color(0xFFF5F5F5),
//        Color(0xFFDAE8FC),
//        Color(0xFFD5E8D4),
//        Color(0xFFFFE6CC),
//        Color(0xFFFFF2CC),
//        Color(0xFFF8CECC),
//        Color(0xFFE1D5E7),
//        Color(0xFF03A9F4),
//        Color(0xFF2196F3),
//        Color(0xFF8BC34A),
//        Color(0xFF4CAF50),
//        Color(0xFF009688),
//        Color(0xFFFFEB3B),
//        Color(0xFFFFC107),
//        Color(0xFFF44336),
//        Color(0xFFE91E63),
//        Color(0xFF9C27B0),
//        Color(0xFF673AB7),
//    )
}


val BottomSheetContainerColor = Color(0xFFF2F2F2)
val FloatingAddRecordButton = Color(0xFF27AE60)

val LightColors = lightColorScheme(
    primary = Teal200,
    onPrimary = Teal200,
    primaryContainer = Purple200,
    onPrimaryContainer = Purple200,
//    inversePrimary = Color.White,
    secondary = Purple700,
    onSecondary = Purple700,
    secondaryContainer = Purple700,
    onSecondaryContainer = Purple700,
//    tertiary = Color.White,
//    onTertiary = Color.White,
//    tertiaryContainer = Color.White,
//    onTertiaryContainer = Color.White,
    background = Color.White,
    onBackground = Color.White,
    surface = Color.Black,
    onSurface = Color.Black,
    surfaceVariant = Color.Black,
    onSurfaceVariant = Color.Black,
    surfaceTint = Color.Black,
//    inverseSurface = Color.White,
//    inverseOnSurface = Color.White,
    error = Color.Black,
    onError = Color.Black,
//    errorContainer = Color.White,
//    onErrorContainer = Color.White,
//    outline = Color.White,
//    outlineVariant = Color.White,
//    scrim = Color.White,
)

val DarkColors = darkColorScheme(
    primary = Teal200,
    onPrimary = Teal200,
    primaryContainer = Purple200,
    onPrimaryContainer = Purple200,
//    inversePrimary = Color.White,
    secondary = Purple700,
    onSecondary = Purple700,
    secondaryContainer = Purple700,
    onSecondaryContainer = Purple700,
//    tertiary = Color.White,
//    onTertiary = Color.White,
//    tertiaryContainer = Color.White,
//    onTertiaryContainer = Color.White,
    background = Color.White,
    onBackground = Color.White,
    surface = Color.Black,
    onSurface = Color.Black,
    surfaceVariant = Color.Black,
    onSurfaceVariant = Color.Black,
    surfaceTint = Color.Black,
//    inverseSurface = Color.White,
//    inverseOnSurface = Color.White,
    error = Color.Black,
    onError = Color.Black,
//    errorContainer = Color.White,
//    onErrorContainer = Color.White,
//    outline = Color.White,
//    outlineVariant = Color.White,
//    scrim = Color.White,
)


val md_theme_light_primary = Color(0xFF825500)
val md_theme_light_onPrimary = Color(0xFFFFFFFF)
val md_theme_light_primaryContainer = Color(0xFFFFDDB3)
val md_theme_light_onPrimaryContainer = Color(0xFF291800)
val md_theme_light_secondary = Color(0xFF6F5B40)
val md_theme_light_onSecondary = Color(0xFFFFFFFF)
val md_theme_light_secondaryContainer = Color(0xFFFBDEBC)
val md_theme_light_onSecondaryContainer = Color(0xFF271904)
val md_theme_light_tertiary = Color(0xFF51643F)
val md_theme_light_onTertiary = Color(0xFFFFFFFF)
val md_theme_light_tertiaryContainer = Color(0xFFD4EABB)
val md_theme_light_onTertiaryContainer = Color(0xFF102004)
val md_theme_light_error = Color(0xFFBA1A1A)
val md_theme_light_errorContainer = Color(0xFFFFDAD6)
val md_theme_light_onError = Color(0xFFFFFFFF)
val md_theme_light_onErrorContainer = Color(0xFF410002)
val md_theme_light_background = Color(0xFFFFFBFF)
val md_theme_light_onBackground = Color(0xFF1F1B16)
val md_theme_light_surface = Color(0xFFFFFBFF)
val md_theme_light_onSurface = Color(0xFF1F1B16)
val md_theme_light_surfaceVariant = Color(0xFFF0E0CF)
val md_theme_light_onSurfaceVariant = Color(0xFF4F4539)
val md_theme_light_outline = Color(0xFF817567)
val md_theme_light_inverseOnSurface = Color(0xFFF9EFE7)
val md_theme_light_inverseSurface = Color(0xFF34302A)
val md_theme_light_inversePrimary = Color(0xFFFFB951)
val md_theme_light_shadow = Color(0xFF000000)
val md_theme_light_surfaceTint = Color(0xFF825500)
val md_theme_light_outlineVariant = Color(0xFFD3C4B4)
val md_theme_light_scrim = Color(0xFF000000)

val LightColorsMain = lightColorScheme(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    primaryContainer = md_theme_light_primaryContainer,
    onPrimaryContainer = md_theme_light_onPrimaryContainer,
    secondary = md_theme_light_secondary,
    onSecondary = md_theme_light_onSecondary,
    secondaryContainer = md_theme_light_secondaryContainer,
    onSecondaryContainer = md_theme_light_onSecondaryContainer,
    tertiary = md_theme_light_tertiary,
    onTertiary = md_theme_light_onTertiary,
    tertiaryContainer = md_theme_light_tertiaryContainer,
    onTertiaryContainer = md_theme_light_onTertiaryContainer,
    error = md_theme_light_error,
    errorContainer = md_theme_light_errorContainer,
    onError = md_theme_light_onError,
    onErrorContainer = md_theme_light_onErrorContainer,
    background = md_theme_light_background,
    onBackground = md_theme_light_onBackground,
    surface = md_theme_light_surface,
    onSurface = md_theme_light_onSurface,
    surfaceVariant = md_theme_light_surfaceVariant,
    onSurfaceVariant = md_theme_light_onSurfaceVariant,
    outline = md_theme_light_outline,
    inverseOnSurface = md_theme_light_inverseOnSurface,
    inverseSurface = md_theme_light_inverseSurface,
    inversePrimary = md_theme_light_inversePrimary,
    surfaceTint = md_theme_light_surfaceTint,
    outlineVariant = md_theme_light_outlineVariant,
    scrim = md_theme_light_scrim,
)
