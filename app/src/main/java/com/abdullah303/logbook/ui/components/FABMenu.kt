package com.abdullah303.logbook.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingActionButtonMenu
import androidx.compose.material3.FloatingActionButtonMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleFloatingActionButton
import androidx.compose.material3.ToggleFloatingActionButtonDefaults.animateIcon
import androidx.compose.material3.animateFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.CustomAccessibilityAction
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.customActions
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.semantics.traversalIndex

/**
 * data class representing a menu item in the fab menu
 * @param icon the icon to display for the menu item
 * @param text the text label for the menu item
 * @param onClick the action to perform when the menu item is clicked
 */
data class FABMenuItem(
    val icon: ImageVector,
    val text: String,
    val onClick: () -> Unit
)

/**
 * generic floating action button menu component
 * @param modifier modifier to apply to the fab menu
 * @param items list of menu items to display
 * @param expanded whether the menu is currently expanded
 * @param onExpandedChange callback when the expanded state changes
 * @param visible whether the fab should be visible (useful for scroll-based visibility)
 * @param alignment alignment of the fab within its container
 * @param contentDescription accessibility content description for the toggle button
 */
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun FABMenu(
    modifier: Modifier = Modifier,
    items: List<FABMenuItem>,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    visible: Boolean = true,
    alignment: Alignment = Alignment.BottomEnd,
    contentDescription: String = "Toggle menu"
) {
    BackHandler(expanded) { onExpandedChange(false) }

    FloatingActionButtonMenu(
        modifier = modifier,
        expanded = expanded,
        button = {
            ToggleFloatingActionButton(
                modifier = Modifier
                    .semantics {
                        traversalIndex = -1f
                        stateDescription = if (expanded) "Expanded" else "Collapsed"
                        this.contentDescription = contentDescription
                    }
                    .animateFloatingActionButton(
                        visible = visible || expanded,
                        alignment = alignment,
                    ),
                checked = expanded,
                onCheckedChange = { onExpandedChange(!expanded) },
            ) {
                val imageVector by remember {
                    derivedStateOf {
                        if (checkedProgress > 0.5f) Icons.Filled.Close else Icons.Filled.Add
                    }
                }
                Icon(
                    imageVector = imageVector,
                    contentDescription = null,
                    modifier = Modifier.animateIcon({ checkedProgress }),
                )
            }
        },
    ) {
        items.forEachIndexed { index, item ->
            FloatingActionButtonMenuItem(
                modifier = Modifier.semantics {
                    isTraversalGroup = true
                    // add custom a11y action to allow closing the menu when focusing
                    // the last menu item, since the close button comes before the first
                    // menu item in the traversal order.
                    if (index == items.size - 1) {
                        customActions = listOf(
                            CustomAccessibilityAction(
                                label = "Close menu",
                                action = {
                                    onExpandedChange(false)
                                    true
                                },
                            )
                        )
                    }
                },
                onClick = {
                    item.onClick()
                    onExpandedChange(false)
                },
                icon = { Icon(item.icon, contentDescription = null) },
                text = { Text(text = item.text) },
            )
        }
    }
}

/**
 * convenience composable that manages its own expanded state
 * @param modifier modifier to apply to the fab menu
 * @param items list of menu items to display
 * @param visible whether the fab should be visible (useful for scroll-based visibility)
 * @param alignment alignment of the fab within its container
 * @param contentDescription accessibility content description for the toggle button
 */
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun FABMenu(
    modifier: Modifier = Modifier,
    items: List<FABMenuItem>,
    visible: Boolean = true,
    alignment: Alignment = Alignment.BottomEnd,
    contentDescription: String = "Toggle menu"
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    
    FABMenu(
        modifier = modifier,
        items = items,
        expanded = expanded,
        onExpandedChange = { expanded = it },
        visible = visible,
        alignment = alignment,
        contentDescription = contentDescription
    )
} 