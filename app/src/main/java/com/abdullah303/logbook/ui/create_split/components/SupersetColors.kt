package com.abdullah303.logbook.ui.create_split.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// utility function to generate consistent colors for superset groups
@Composable
fun getSupersetColor(supersetGroupId: String): Color {
    val colors = listOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.secondary,
        MaterialTheme.colorScheme.tertiary,
        Color(0xFF6B73FF), // purple
        Color(0xFF9C27B0), // deep purple
        Color(0xFF00BCD4), // cyan
        Color(0xFF4CAF50), // green
        Color(0xFFFF9800), // orange
    )
    
    // use hash of the group id to pick a consistent color
    val colorIndex = supersetGroupId.hashCode().let { hash ->
        if (hash < 0) -hash else hash
    } % colors.size
    
    return colors[colorIndex]
} 