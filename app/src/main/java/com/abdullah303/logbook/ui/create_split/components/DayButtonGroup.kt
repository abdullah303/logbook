package com.abdullah303.logbook.ui.create_split.components

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.abdullah303.logbook.ui.components.InlineEditableText

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun DayButtonGroup(
    days: List<String>,
    selectedDayIndex: Int,
    onDaySelected: (Int) -> Unit,
    onAddDay: () -> Unit,
    onLongPressDay: (Int, String) -> Unit,
    onPositionUpdate: (Int, Offset) -> Unit = { _, _ -> },
    modifier: Modifier = Modifier,
    renamingIndex: Int? = null,
    onRenameDay: (Int, String) -> Unit = { _, _ -> },
    onRenameDayFinished: () -> Unit = {}
) {
    if (days.size <= 2) {
        // simple row if there are two or fewer days
        Row(
            modifier = modifier.padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(ButtonGroupDefaults.ConnectedSpaceBetween),
        ) {
            // show each day button
            days.forEachIndexed { index, dayLabel ->
                val interactionSource = remember { MutableInteractionSource() }
                ToggleButton(
                    checked = selectedDayIndex == index,
                    onCheckedChange = { if (renamingIndex != index) onDaySelected(index) },
                    modifier = Modifier
                        .weight(1f)
                        .semantics { role = Role.RadioButton },
                    shapes = when (index) {
                        0 -> ButtonGroupDefaults.connectedLeadingButtonShapes()
                        days.lastIndex -> ButtonGroupDefaults.connectedMiddleButtonShapes()
                        else -> ButtonGroupDefaults.connectedMiddleButtonShapes()
                    },
                    interactionSource = interactionSource,
                    enabled = renamingIndex != index
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .onGloballyPositioned { coordinates ->
                                onPositionUpdate(index, coordinates.localToWindow(Offset.Zero))
                            }
                            .combinedClickable(
                                enabled = renamingIndex != index,
                                onClick = { onDaySelected(index) },
                                onLongClick = { onLongPressDay(index, dayLabel) },
                                interactionSource = interactionSource,
                                indication = null
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        if (renamingIndex == index) {
                            InlineEditableText(
                                text = dayLabel,
                                onTextChange = { onRenameDay(index, it) },
                                onDone = onRenameDayFinished,
                                startInEditMode = true,
                                singleLine = true,
                                textStyle = LocalTextStyle.current.copy(
                                    textAlign = TextAlign.Center
                                )
                            )
                        } else {
                            Text(dayLabel)
                        }
                    }
                }
            }

            // add button at the end
            ToggleButton(
                checked = false,
                onCheckedChange = { onAddDay() },
                modifier = Modifier.weight(0.3f),
                shapes = ButtonGroupDefaults.connectedTrailingButtonShapes(),
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "add new day"
                )
            }
        }
    } else {
        // if there are more than two days, make it scrollable
        LazyRow(
            modifier = modifier.padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(ButtonGroupDefaults.ConnectedSpaceBetween),
        ) {
            itemsIndexed(days) { index, dayLabel ->
                val interactionSource = remember { MutableInteractionSource() }
                ToggleButton(
                    checked = selectedDayIndex == index,
                    onCheckedChange = { if (renamingIndex != index) onDaySelected(index) },
                    modifier = Modifier
                        .width(120.dp)
                        .semantics { role = Role.RadioButton },
                    shapes = when (index) {
                        0 -> ButtonGroupDefaults.connectedLeadingButtonShapes()
                        days.lastIndex -> ButtonGroupDefaults.connectedMiddleButtonShapes()
                        else -> ButtonGroupDefaults.connectedMiddleButtonShapes()
                    },
                    interactionSource = interactionSource,
                    enabled = renamingIndex != index
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .onGloballyPositioned { coordinates ->
                                onPositionUpdate(index, coordinates.localToWindow(Offset.Zero))
                            }
                            .combinedClickable(
                                enabled = renamingIndex != index,
                                onClick = { onDaySelected(index) },
                                onLongClick = { onLongPressDay(index, dayLabel) },
                                interactionSource = interactionSource,
                                indication = null
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        if (renamingIndex == index) {
                            InlineEditableText(
                                text = dayLabel,
                                onTextChange = { onRenameDay(index, it) },
                                onDone = onRenameDayFinished,
                                startInEditMode = true,
                                singleLine = true,
                                textStyle = LocalTextStyle.current.copy(
                                    textAlign = TextAlign.Center
                                )
                            )
                        } else {
                            Text(dayLabel)
                        }
                    }
                }
            }

            // add button for creating a new day
            item {
                ToggleButton(
                    checked = false,
                    onCheckedChange = { onAddDay() },
                    shapes = ButtonGroupDefaults.connectedTrailingButtonShapes(),
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "add new day",
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
} 