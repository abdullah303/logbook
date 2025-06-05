package com.abdullah303.logbook.core.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ValueSelectionScreen(
    navController: NavController,
    title: String,
    selectedValue: String = "",
    unit: String = "",
    valueOptions: List<String>,
    onValueSelected: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var manualInput by remember { mutableStateOf("") }
    var selectedValueState by remember { mutableStateOf(selectedValue) }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(selectedValue) {
        selectedValueState = selectedValue
        if (selectedValue.isNotEmpty() && !valueOptions.contains(selectedValue)) {
            manualInput = selectedValue
        }
    }

    // Update highlighted value based on scroll position with smooth animation
    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .collect { index ->
                if (index >= 0 && index < valueOptions.size) {
                    selectedValueState = valueOptions[index]
                }
            }
    }

    // Scroll to highlighted value when it changes from manual input
    LaunchedEffect(manualInput) {
        if (manualInput.isNotEmpty() && valueOptions.contains(manualInput)) {
            val index = valueOptions.indexOf(manualInput)
            if (index != -1) {
                coroutineScope.launch {
                    listState.scrollToItem(index)
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Value options
            Box(
                modifier = Modifier.weight(1f)
            ) {
            LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(1.dp),
                    contentPadding = PaddingValues(top = 300.dp, bottom = 400.dp)
            ) {
                    items(valueOptions.size) { index ->
                        val value = valueOptions[index]
                        val isSelected = value == selectedValueState

                        // Animated values with professional spring physics
                        val scale by animateFloatAsState(
                            targetValue = if (isSelected) 1.2f else 1f,
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessLow
                            ),
                            label = "scale"
                        )

                        val elevation by animateDpAsState(
                            targetValue = if (isSelected) 8.dp else 1.dp,
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessLow
                            ),
                            label = "elevation"
                        )

                        // Calculate spacing based on distance from selected item
                        val distance = abs(index - valueOptions.indexOf(selectedValueState))
                        val spacing = when {
                            distance == 0 -> 16.dp  // Selected item
                            distance == 1 -> 8.dp   // Adjacent items
                            distance == 2 -> 4.dp   // Near items
                            distance <= 4 -> 2.dp   // Medium distance
                            else -> 1.dp           // Far items
                        }

                        // Add spacing before the card
                        Spacer(modifier = Modifier.height(spacing))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                                .scale(scale)
                                .animateContentSize(
                                    animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioMediumBouncy,
                                        stiffness = Spring.StiffnessLow
                                    )
                                )
                                .clickable {
                                    manualInput = ""
                                    selectedValueState = value
                                    onValueSelected(value)
                                },
                colors = CardDefaults.cardColors(
                                containerColor = if (isSelected)
                                    MaterialTheme.colorScheme.primaryContainer
                                else
                                    MaterialTheme.colorScheme.surfaceContainerLowest
                ),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = elevation
                            )
            ) {
                            Box(
                    modifier = Modifier
                        .fillMaxWidth()
                                    .padding(vertical = 20.dp, horizontal = 16.dp),
                                contentAlignment = Alignment.Center
                ) {
                    Text(
                                    text = "$value $unit",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontSize = if (isSelected) 24.sp else 18.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                    ),
                                    color = if (isSelected)
                                        MaterialTheme.colorScheme.onPrimaryContainer
                                    else
                                        MaterialTheme.colorScheme.onSurface
                    )
                            }
                        }
                        
                        // Add spacing after the card
                        Spacer(modifier = Modifier.height(spacing))
                    }
                }
            }

            // Manual input field at the bottom
            PillTextField(
                        value = manualInput,
                onValueChange = {
                    manualInput = it
                    if (it.isNotEmpty() && valueOptions.contains(it)) {
                        selectedValueState = it
                    } else {
                                selectedValueState = ""
                            }
                        },
                label = { Text("Manual Input") },
                        modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                trailingIcon = {
                    if (manualInput.isNotEmpty()) {
                        IconButton(onClick = { onValueSelected(manualInput) }) {
                            Icon(Icons.Default.Check, contentDescription = "Confirm")
                    }
                }
            }
            )
        }
    }
}

