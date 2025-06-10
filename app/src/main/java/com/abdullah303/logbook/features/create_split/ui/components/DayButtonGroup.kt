package com.abdullah303.logbook.features.create_split.ui.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun DayButtonGroup(
    days: List<String>,
    selectedDayIndex: Int,
    onDaySelected: (Int) -> Unit,
    isRenamingInline: Boolean,
    onRenameComplete: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var newDayName by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(isRenamingInline) {
        if (isRenamingInline) {
            newDayName = ""
            focusRequester.requestFocus()
        }
    }

    val scrollState = rememberScrollState()
    Row(
        modifier = modifier
            .padding(horizontal = 8.dp)
            .then(
                if (days.size > 3) {
                    Modifier.horizontalScroll(scrollState)
                } else {
                    Modifier
                }
            ),
        horizontalArrangement = Arrangement.spacedBy(ButtonGroupDefaults.ConnectedSpaceBetween),
    ) {
        days.forEachIndexed { index, label ->
            ToggleButton(
                checked = selectedDayIndex == index,
                onCheckedChange = { onDaySelected(index) },
                modifier = Modifier
                    .then(
                        if (days.size > 3) {
                            Modifier.width(120.dp)
                        } else {
                            Modifier.weight(1f)
                        }
                    )
                    .semantics { role = Role.RadioButton },
                shapes = when (index) {
                    0 -> ButtonGroupDefaults.connectedLeadingButtonShapes()
                    days.lastIndex -> ButtonGroupDefaults.connectedTrailingButtonShapes()
                    else -> ButtonGroupDefaults.connectedMiddleButtonShapes()
                },
            ) {
                Icon(
                    if (selectedDayIndex == index) Icons.Filled.CalendarToday
                    else Icons.Outlined.CalendarToday,
                    contentDescription = "Day ${index + 1}",
                )
                Spacer(Modifier.size(ToggleButtonDefaults.IconSpacing))

                if (isRenamingInline && selectedDayIndex == index) {
                    // Inline editing field
                    BasicTextField(
                        value = newDayName,
                        onValueChange = { newDayName = it },
                        singleLine = true,
                        textStyle = TextStyle(
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                            color = Color.Black,
                            textAlign = TextAlign.Start
                        ),
                        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                        modifier = Modifier
                            .weight(1f)
                            .focusRequester(focusRequester)
                            .onPreviewKeyEvent { keyEvent ->
                                if (keyEvent.key == Key.Enter && keyEvent.type == KeyEventType.KeyUp) {
                                    // Commit rename
                                    if (newDayName.isNotBlank()) {
                                        onRenameComplete(newDayName)
                                    }
                                    true
                                } else false
                            },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                if (newDayName.isNotBlank()) {
                                    onRenameComplete(newDayName)
                                }
                            }
                        )
                    )
                } else {
                    Text(label, modifier = Modifier.weight(1f))
                }
            }
        }
    }
} 