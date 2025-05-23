package com.abdullah303.logbook.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

/**
 * Application-wide shapes for Material3 theme.
 */
val AppShapes: Shapes = Shapes(
    small = RoundedCornerShape(4.dp),   // used for small components like buttons
    medium = RoundedCornerShape(8.dp),  // default shape for cards and surfaces
    large = RoundedCornerShape(16.dp)   // larger rounding for dialogs and banners
)
