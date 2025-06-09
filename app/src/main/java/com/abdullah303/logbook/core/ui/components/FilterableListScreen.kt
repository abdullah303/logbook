package com.abdullah303.logbook.core.ui.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.role
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun <T> FilterableListScreen(
    title: String,
    filterOptions: List<FilterOption>,
    items: List<T>,
    selectedFilterIndex: Int = 0,
    onFilterSelected: (Int) -> Unit = {},
    itemContent: @Composable (T) -> Unit,
    fabContent: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    searchQuery: String = "",
    onSearchQueryChange: (String) -> Unit = {},
    isLoading: Boolean = false,
    error: String? = null
) {
    var selectedIndex by remember { mutableStateOf(selectedFilterIndex) }
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) }
            )
        },
        floatingActionButton = {
            fabContent?.invoke()
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Search bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                placeholder = { Text("Search...") },
                singleLine = true,
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) }
            )
            // Scrollable button group
            Row(
                Modifier
                    .horizontalScroll(scrollState)
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(ButtonGroupDefaults.ConnectedSpaceBetween),
                verticalAlignment = Alignment.CenterVertically
            ) {
                filterOptions.forEachIndexed { index, option ->
                    ToggleButton(
                        checked = selectedIndex == index,
                        onCheckedChange = {
                            selectedIndex = index
                            onFilterSelected(index)
                        },
                        shapes = when (index) {
                            0 -> ButtonGroupDefaults.connectedLeadingButtonShapes()
                            filterOptions.lastIndex -> ButtonGroupDefaults.connectedTrailingButtonShapes()
                            else -> ButtonGroupDefaults.connectedMiddleButtonShapes()
                        },
                        modifier = Modifier.semantics { role = Role.RadioButton },
                    ) {
                        option.icon?.let {
                            Icon(
                                it,
                                contentDescription = option.label,
                            )
                            Spacer(Modifier.size(ToggleButtonDefaults.IconSpacing))
                        }
                        Text(option.label)
                    }
                }
            }
            Divider()

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                when {
                    isLoading -> {
                        CircularProgressIndicator()
                    }
                    error != null -> {
                        Text(
                            text = error,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                    items.isEmpty() -> {
                        Text(
                            text = "No items found",
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                    else -> {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(items) { item ->
                                itemContent(item)
                            }
                        }
                    }
                }
            }
        }
    }
}

data class FilterOption(val label: String, val icon: ImageVector? = null) 