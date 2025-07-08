package com.abdullah303.logbook.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue

@Composable
fun InlineEditableText(
    text: String,
    modifier: Modifier = Modifier,
    placeholder: String = "Click to edit",
    onTextChange: (String) -> Unit,
    textStyle: TextStyle = LocalTextStyle.current,
    singleLine: Boolean = false,
    onDone: () -> Unit = {},
    startInEditMode: Boolean = false
) {
    var isEditing by remember(startInEditMode) { mutableStateOf(startInEditMode) }
    val focusRequester = remember { FocusRequester() }

    Box(
        modifier = modifier.clickable(
            enabled = !isEditing,
            interactionSource = remember { MutableInteractionSource() },
            indication = null
        ) {
            isEditing = true
        }
    ) {
        if (isEditing) {
            val focusManager = LocalFocusManager.current
            BasicTextField(
                value = TextFieldValue(
                    text = text,
                    selection = TextRange(text.length)
                ),
                onValueChange = { onTextChange(it.text) },
                modifier = Modifier
                    .focusRequester(focusRequester),
                textStyle = textStyle,
                singleLine = singleLine,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    onDone()
                    isEditing = false
                    focusManager.clearFocus()
                }),
                cursorBrush = SolidColor(textStyle.color)
            )

            LaunchedEffect(Unit) {
                focusRequester.requestFocus()
            }
        } else {
            Text(
                text = if (text.isEmpty()) placeholder else text,
                style = textStyle,
                maxLines = if (singleLine) 1 else Int.MAX_VALUE,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
} 