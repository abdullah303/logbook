package com.abdullah303.logbook.features.settings.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.abdullah303.logbook.R

@Composable
fun WeightPlateSelector(
    enabledPlates: Map<Float, Boolean>,
    onPlateToggle: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    var isExpanded by remember { mutableStateOf(false) }
    val colorScheme = MaterialTheme.colorScheme

    // Standard Olympic plate colors
    val plateColors = mapOf(
        1.25f to Color(0xFF9E9E9E), // Gray
        2.5f to Color(0xFF000000),  // Black
        5.0f to Color(0xFFFFFFFF),  // White
        10.0f to Color(0xFF4CAF50), // Green
        15.0f to Color(0xFFFFFF00), // Yellow
        20.0f to Color(0xFF2196F3), // Blue
        25.0f to Color(0xFFF44336)  // Red
    )

    Column(modifier = modifier) {
        // Collapsed state - just show the barbell with current plates
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = colorScheme.surface,
            tonalElevation = 2.dp,
            shape = MaterialTheme.shapes.medium,
            onClick = { isExpanded = !isExpanded }
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .padding(16.dp)
            ) {
                val centerY = size.height * 0.5f
                val barbellLength = size.width * 0.8f
                val barbellStartX = 0f
                val barbellEndX = barbellStartX + barbellLength
                val barbellHeight = 24f
                val scale = size.width / 1.2f

                // Draw barbell
                drawRect(
                    color = colorScheme.outlineVariant,
                    topLeft = Offset(barbellStartX, centerY - barbellHeight/2),
                    size = Size(barbellLength, barbellHeight)
                )

                // Get enabled plates and sort by weight
                val enabledPlatesList = enabledPlates.entries
                    .filter { it.value }
                    .sortedByDescending { it.key }
                    .map { it.key }

                // Calculate total width needed for all plates
                var totalWidth = 0f
                enabledPlatesList.forEach { weight ->
                    val thickness = (weight / 25f) * scale * 0.15f
                    totalWidth += thickness
                }

                // Ensure we have enough space
                val availableWidth = barbellLength * 0.6f
                val scaleFactor = if (totalWidth > availableWidth) {
                    availableWidth / totalWidth
                } else {
                    1f
                }

                // Calculate plate start position to center the plates on the barbell
                val plateStartX = barbellStartX + (barbellLength - availableWidth) / 2

                // Draw plates starting from the left
                var currentOffset = 0f
                enabledPlatesList.forEach { weight ->
                    val plateColor = plateColors[weight] ?: Color.Gray
                    val thickness = (weight / 25f) * scale * 0.15f * scaleFactor
                    // Adjust plate heights based on weight - increased heights
                    val plateHeight = when (weight) {
                        1.25f -> size.height * 0.5f
                        2.5f -> size.height * 0.55f
                        5.0f -> size.height * 0.6f
                        10.0f -> size.height * 0.7f
                        15.0f -> size.height * 0.75f
                        20.0f -> size.height * 0.8f
                        25.0f -> size.height * 0.85f
                        else -> size.height * 0.6f
                    }

                    // Add spacing between plates
                    currentOffset += 4f

                    // Draw plate shadow
                    drawRoundRect(
                        color = Color.Black.copy(alpha = 0.2f),
                        topLeft = Offset(plateStartX + currentOffset + 2f, centerY - plateHeight/2 + 2f),
                        size = Size(thickness, plateHeight),
                        cornerRadius = CornerRadius(6f)
                    )

                    // Draw plate with rounded corners and gradient effect
                    drawRoundRect(
                        color = plateColor,
                        topLeft = Offset(plateStartX + currentOffset, centerY - plateHeight/2),
                        size = Size(thickness, plateHeight),
                        cornerRadius = CornerRadius(6f)
                    )

                    // Draw plate highlight (top edge)
                    drawRoundRect(
                        color = plateColor.copy(alpha = 0.7f),
                        topLeft = Offset(plateStartX + currentOffset, centerY - plateHeight/2),
                        size = Size(thickness, plateHeight * 0.1f),
                        cornerRadius = CornerRadius(6f)
                    )

                    // Draw plate outline
                    drawRoundRect(
                        color = colorScheme.outline,
                        topLeft = Offset(plateStartX + currentOffset, centerY - plateHeight/2),
                        size = Size(thickness, plateHeight),
                        cornerRadius = CornerRadius(6f),
                        style = Stroke(width = 1f)
                    )

                    currentOffset += thickness
                }

                // Draw collars
                val collarWidth = 8f
                drawRect(
                    color = colorScheme.outline,
                    topLeft = Offset(plateStartX - collarWidth, centerY - barbellHeight * 1.2f),
                    size = Size(collarWidth, barbellHeight * 2.4f)
                )
            }
        }

        // Expanded state - show plate selection carousel
        AnimatedVisibility(
            visible = isExpanded,
            enter = expandVertically() + fadeIn(),
            exit = shrinkVertically() + fadeOut()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(0.dp)
                ) {
                    WeightPlate(25.0f, enabledPlates[25.0f] ?: false, onPlateToggle)
                    WeightPlate(20.0f, enabledPlates[20.0f] ?: false, onPlateToggle)
                    WeightPlate(15.0f, enabledPlates[15.0f] ?: false, onPlateToggle)
                    WeightPlate(10.0f, enabledPlates[10.0f] ?: false, onPlateToggle)
                    WeightPlate(5.0f, enabledPlates[5.0f] ?: false, onPlateToggle)
                    WeightPlate(2.5f, enabledPlates[2.5f] ?: false, onPlateToggle)
                    WeightPlate(1.25f, enabledPlates[1.25f] ?: false, onPlateToggle)
                }
            }
        }
    }
}

@Composable
private fun WeightPlate(
    weight: Float,
    isEnabled: Boolean,
    onToggle: (Float) -> Unit
) {
    val iconRes = when (weight) {
        1.25f -> R.drawable.weight_plate_1_25kg
        2.5f -> R.drawable.weight_plate_2_5kg
        5.0f -> R.drawable.weight_plate_5kg
        10.0f -> R.drawable.weight_plate_10kg
        15.0f -> R.drawable.weight_plate_15kg
        20.0f -> R.drawable.weight_plate_20kg
        25.0f -> R.drawable.weight_plate_25kg
        else -> R.drawable.weight_plate_1_25kg
    }

    Surface(
        modifier = Modifier.size(140.dp),
        shape = CircleShape,
        color = Color.Transparent,
        onClick = { onToggle(weight) }
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = "${weight}kg plate",
                modifier = Modifier
                    .size(120.dp)
                    .graphicsLayer(alpha = if (isEnabled) 1f else 0.4f),
                tint = Color.Unspecified
            )
        }
    }
} 