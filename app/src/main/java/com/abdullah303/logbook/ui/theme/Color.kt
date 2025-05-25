package com.abdullah303.logbook.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

/**
 * Rosé Pine color schemes (main variant for dark theme, dawn variant for light theme)
 * Mapped to Material 3 color roles following Material Design guidelines
 * Source: Rosé Pine palette from rosepinetheme.com
 */
val LightColors: ColorScheme = lightColorScheme(
    // Primary colors - using pine (dawn) as the primary brand color
    primary = Color(0xFF286983),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFC4E7FF),
    onPrimaryContainer = Color(0xFF001E2E),

    // Secondary colors - using rose (dawn) for secondary actions
    secondary = Color(0xFFD7827E),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFFFDAD8),
    onSecondaryContainer = Color(0xFF410002),

    // Tertiary colors - using gold (dawn) for tertiary actions
    tertiary = Color(0xFFEA9D34),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFFFDDB6),
    onTertiaryContainer = Color(0xFF2D1600),

    // Surface colors - using base and surface (dawn) for different elevation levels
    surface = Color(0xFFFFFAF3),
    onSurface = Color(0xFF575279),
    surfaceVariant = Color(0xFFFAF4ED),
    onSurfaceVariant = Color(0xFF575279),

    // Background colors
    background = Color(0xFFFAF4ED),
    onBackground = Color(0xFF575279),

    // Error colors - using love (dawn) for error states
    error = Color(0xFFB4637A),
    onError = Color(0xFFFFFFFF),
    errorContainer = Color(0xFFFFDAD8),
    onErrorContainer = Color(0xFF410002),

    // Outline colors
    outline = Color(0xFF575279),
    outlineVariant = Color(0xFF575279).copy(alpha = 0.5f),

    // Scrim color
    scrim = Color(0xFF000000).copy(alpha = 0.32f)
)

val DarkColors: ColorScheme = darkColorScheme(
    // Primary colors - using pine (main) as the primary brand color
    primary = Color(0xFF31748F),
    onPrimary = Color(0xFFE0DEF4),
    primaryContainer = Color(0xFF004B63),
    onPrimaryContainer = Color(0xFFC4E7FF),

    // Secondary colors - using rose (main) for secondary actions
    secondary = Color(0xFFEBBCBA),
    onSecondary = Color(0xFFE0DEF4),
    secondaryContainer = Color(0xFF8B4B4B),
    onSecondaryContainer = Color(0xFFFFDAD8),

    // Tertiary colors - using gold (main) for tertiary actions
    tertiary = Color(0xFFF6C177),
    onTertiary = Color(0xFFE0DEF4),
    tertiaryContainer = Color(0xFF8B4B00),
    onTertiaryContainer = Color(0xFFFFDDB6),

    // Surface colors - using base and surface (main) for different elevation levels
    surface = Color(0xFF1F1D2E),
    onSurface = Color(0xFFE0DEF4),
    surfaceVariant = Color(0xFF191724),
    onSurfaceVariant = Color(0xFFE0DEF4),

    // Background colors
    background = Color(0xFF191724),
    onBackground = Color(0xFFE0DEF4),

    // Error colors - using love (main) for error states
    error = Color(0xFFEB6F92),
    onError = Color(0xFFE0DEF4),
    errorContainer = Color(0xFF8B4B4B),
    onErrorContainer = Color(0xFFFFDAD8),

    // Outline colors
    outline = Color(0xFFE0DEF4),
    outlineVariant = Color(0xFFE0DEF4).copy(alpha = 0.5f),

    // Scrim color
    scrim = Color(0xFF000000).copy(alpha = 0.32f)
)

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)
