package com.abdullah303.logbook.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SegmentedControl(
    modifier: Modifier = Modifier,
    items: List<String>,
    defaultSelectedItemIndex: Int = 0,
    useFixedWidth: Boolean = false,
    itemWidth: Dp = 120.dp,
    cornerRadius: Int = 24,
    onItemSelection: (selectedItemIndex: Int) -> Unit
) {
    var selectedIndex by remember { mutableStateOf(defaultSelectedItemIndex) }

    // Update selectedIndex when defaultSelectedItemIndex changes
    LaunchedEffect(defaultSelectedItemIndex) {
        selectedIndex = defaultSelectedItemIndex
    }

    Card(
        modifier = Modifier
            .then(
                if (useFixedWidth) {
                    Modifier.width(itemWidth * items.size)
                } else {
                    Modifier.fillMaxWidth()
                }
            )
            .height(38.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        shape = RoundedCornerShape(cornerRadius)
    ) {
        Row(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceVariant),
            horizontalArrangement = Arrangement.Center
        ) {
            items.forEachIndexed { index, item ->
                Card(
                    modifier = modifier
                        .weight(1f)
                        .padding(2.dp),
                    onClick = {
                        selectedIndex = index
                        onItemSelection(selectedIndex)
                    },
                    colors = CardDefaults.cardColors(
                        containerColor = if (selectedIndex == index) {
                            MaterialTheme.colorScheme.primaryContainer
                        } else {
                            MaterialTheme.colorScheme.surfaceVariant
                        },
                        contentColor = if (selectedIndex == index)
                            MaterialTheme.colorScheme.onPrimaryContainer
                        else
                            MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    shape = when (index) {
                        0 -> RoundedCornerShape(
                            topStartPercent = cornerRadius,
                            topEndPercent = cornerRadius,
                            bottomStartPercent = cornerRadius,
                            bottomEndPercent = cornerRadius
                        )
                        items.size - 1 -> RoundedCornerShape(
                            topStartPercent = cornerRadius,
                            topEndPercent = cornerRadius,
                            bottomStartPercent = cornerRadius,
                            bottomEndPercent = cornerRadius
                        )
                        else -> RoundedCornerShape(
                            topStartPercent = 0,
                            topEndPercent = 0,
                            bottomStartPercent = 0,
                            bottomEndPercent = 0
                        )
                    },
                ) {
                    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = item,
                            style = LocalTextStyle.current.copy(
                                fontSize = 14.sp,
                                fontWeight = if (selectedIndex == index)
                                    FontWeight.Medium
                                else
                                    FontWeight.Normal,
                                color = if (selectedIndex == index)
                                    MaterialTheme.colorScheme.onPrimaryContainer
                                else
                                    MaterialTheme.colorScheme.onSurfaceVariant
                            ),
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        }
    }
} 