package com.abdullah303.logbook.features.create_split.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun EditableTitle(
    title: String,
    onTitleChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var isRenamingTitle by remember { mutableStateOf(false) }
    var newTitle by remember { mutableStateOf("") }
    val titleFocusRequester = remember { FocusRequester() }

    LaunchedEffect(isRenamingTitle) {
        if (isRenamingTitle) {
            newTitle = ""
            titleFocusRequester.requestFocus()
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        if (isRenamingTitle) {
            BasicTextField(
                value = newTitle,
                onValueChange = { newTitle = it },
                singleLine = true,
                textStyle = TextStyle(
                    fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                ),
                cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .focusRequester(titleFocusRequester)
                    .onPreviewKeyEvent { keyEvent ->
                        if (keyEvent.key == Key.Enter && keyEvent.type == KeyEventType.KeyUp) {
                            if (newTitle.isNotBlank()) {
                                onTitleChanged(newTitle)
                            }
                            isRenamingTitle = false
                            true
                        } else false
                    },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        if (newTitle.isNotBlank()) {
                            onTitleChanged(newTitle)
                        }
                        isRenamingTitle = false
                    }
                )
            )
        } else {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.clickable { 
                    isRenamingTitle = true
                    newTitle = ""
                }
            )
        }
    }
} 