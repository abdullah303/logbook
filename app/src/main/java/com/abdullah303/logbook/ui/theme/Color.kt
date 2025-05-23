package com.abdullah303.logbook.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

/**
 * Rosé Pine color schemes (main variant for dark theme, dawn variant for light theme)
 * Source: Rosé Pine palette roles from rosepinetheme.com ([rosepinetheme.com](https://rosepinetheme.com/ca/palette/ingredients/?utm_source=chatgpt.com))
 */
val LightColors: ColorScheme = lightColorScheme(
    primary = Color(0xFF286983),      // pine (dawn) ([rosepinetheme.com](https://rosepinetheme.com/ca/palette/ingredients/?utm_source=chatgpt.com))
    onPrimary = Color(0xFF575279),    // text (dawn)

    secondary = Color(0xFFD7827E),    // rose (dawn)
    onSecondary = Color(0xFF575279),

    tertiary = Color(0xFFEA9D34),     // gold (dawn)
    onTertiary = Color(0xFF575279),

    background = Color(0xFFFAF4ED),   // base (dawn)
    onBackground = Color(0xFF575279),

    surface = Color(0xFFFFFAF3),      // surface (dawn)
    onSurface = Color(0xFF575279),

    error = Color(0xFFB4637A),        // love (dawn)
    onError = Color(0xFFFFFAF3)
)

val DarkColors: ColorScheme = darkColorScheme(
    primary = Color(0xFF31748F),      // pine (main) ([rosepinetheme.com](https://rosepinetheme.com/ca/palette/ingredients/?utm_source=chatgpt.com))
    onPrimary = Color(0xFFE0DEF4),    // text (main)

    secondary = Color(0xFFEBBCBA),    // rose (main)
    onSecondary = Color(0xFFE0DEF4),

    tertiary = Color(0xFFF6C177),     // gold (main)
    onTertiary = Color(0xFFE0DEF4),

    background = Color(0xFF191724),   // base (main)
    onBackground = Color(0xFFE0DEF4),

    surface = Color(0xFF1F1D2E),      // surface (main)
    onSurface = Color(0xFFE0DEF4),

    error = Color(0xFFEB6F92),        // love (main)
    onError = Color(0xFF1F1D2E)
)
