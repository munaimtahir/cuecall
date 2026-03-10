package com.cuecall.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Clinic-friendly color palette: clean blue/teal on white
private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF0D6EFD),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFD6E4FF),
    onPrimaryContainer = Color(0xFF001B47),
    secondary = Color(0xFF0AA89B),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFBEF0EB),
    onSecondaryContainer = Color(0xFF00201D),
    error = Color(0xFFBA1A1A),
    onError = Color.White,
    background = Color(0xFFF8FAFE),
    onBackground = Color(0xFF1A1C20),
    surface = Color.White,
    onSurface = Color(0xFF1A1C20),
    surfaceVariant = Color(0xFFE8ECF4),
    outline = Color(0xFF8A95A7)
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF9FBFFF),
    onPrimary = Color(0xFF00317B),
    primaryContainer = Color(0xFF0047AF),
    onPrimaryContainer = Color(0xFFD6E4FF),
    secondary = Color(0xFF82D4CC),
    onSecondary = Color(0xFF003733),
    background = Color(0xFF111318),
    onBackground = Color(0xFFE2E2E9),
    surface = Color(0xFF1A1C22),
    onSurface = Color(0xFFE2E2E9)
)

// Larger typography for front-desk clinic use
val CueCallTypography = Typography(
    displayLarge = TextStyle(fontSize = 57.sp, fontWeight = FontWeight.Normal, lineHeight = 64.sp),
    displayMedium = TextStyle(fontSize = 45.sp, fontWeight = FontWeight.Normal, lineHeight = 52.sp),
    headlineLarge = TextStyle(fontSize = 32.sp, fontWeight = FontWeight.Bold, lineHeight = 40.sp),
    headlineMedium = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.SemiBold, lineHeight = 36.sp),
    titleLarge = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.SemiBold, lineHeight = 28.sp),
    titleMedium = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium, lineHeight = 24.sp),
    bodyLarge = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Normal, lineHeight = 24.sp),
    bodyMedium = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Normal, lineHeight = 20.sp),
    labelLarge = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium, lineHeight = 20.sp),
)

@Composable
fun CueCallTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = CueCallTypography,
        content = content
    )
}
