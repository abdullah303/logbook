package com.abdullah303.logbook.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun BubbleContextMenu(
    showMenu: Boolean,
    onDismiss: () -> Unit,
    anchorOffset: Offset,
    buttonWidth: androidx.compose.ui.unit.Dp,
    onRename: () -> Unit,
    onDelete: () -> Unit,
    canDelete: Boolean = true,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current

    if (showMenu) {
        // automatically dismiss the menu after 3 seconds
        LaunchedEffect(showMenu) {
            delay(3000)
            onDismiss()
        }

        Box(
            modifier = modifier.fillMaxSize()
        ) {
            // background that can be clicked to close the menu
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        onDismiss()
                    }
            )

            Card(
                modifier = Modifier
                    .offset {
                        // position it closer to the button
                        val x = with(density) { anchorOffset.x.toDp() + buttonWidth - 80.dp } // reduced offset
                        val y = with(density) { anchorOffset.y.toDp() + 8.dp } // much closer to button
                        IntOffset(
                            x = with(density) { x.toPx().toInt() },
                            y = with(density) { y.toPx().toInt() }
                        )
                    }
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        // stop clicks inside the card from closing the menu
                    },
                elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.98f)
                )
            ) {
                Column(
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    IconButton(
                        onClick = {
                            onDismiss()
                            onRename()
                        },
                        modifier = Modifier.size(44.dp)
                    ) {
                        Icon(
                            Icons.Default.Edit,
                            contentDescription = "rename",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    IconButton(
                        onClick = {
                            onDismiss()
                            if (canDelete) {
                                onDelete()
                            }
                        },
                        modifier = Modifier.size(44.dp),
                        enabled = canDelete
                    ) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "delete",
                            tint = if (canDelete) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
            }
        }
    }
} 