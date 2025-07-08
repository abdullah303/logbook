package com.abdullah303.logbook.ui.components

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation

/**
 * inline editable text component that shows cursor and keyboard when editing
 * @param text the current text value
 * @param modifier modifier to apply to the component
 * @param onTextChange callback when text changes
 * @param textStyle text style for the text
 * @param singleLine whether the text field should be single line
 * @param keyboardOptions keyboard options for the text field
 * @param onDone callback when done action is triggered
 */
@Composable
fun InlineEditableText(
    text: String,
    modifier: Modifier = Modifier,
    onTextChange: (String) -> Unit = {},
    textStyle: TextStyle = LocalTextStyle.current,
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
    onDone: (() -> Unit)? = null,
) {
    var currentText by remember { mutableStateOf(text) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    BasicTextField(
        value = currentText,
        onValueChange = { 
            currentText = it
            onTextChange(it)
        },
        modifier = modifier
            .focusRequester(focusRequester)
            .clickable { focusRequester.requestFocus() },
        textStyle = textStyle.copy(
            color = textStyle.color
        ),
        singleLine = singleLine,
        keyboardOptions = keyboardOptions.copy(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
                onDone?.invoke()
            }
        ),
        cursorBrush = SolidColor(textStyle.color )
    )
} 