package com.abdullah303.logbook.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.math.abs

// sealed class to define different selection types
sealed class ValueSelectionType<T> {
    data class SingleFloat(
        val values: List<Float>,
        val initialValue: Float,
        val formatter: (Float) -> String = { if (it % 1f == 0f) it.toInt().toString() else String.format("%.1f", it) }
    ) : ValueSelectionType<Float>()

    data class WeightSelection(
        val values: List<Float>,
        val initialValue: Float,
        val unit: String,
        val formatter: (Float) -> String = { if (it % 1f == 0f) it.toInt().toString() else String.format("%.1f", it) }
    ) : ValueSelectionType<Float>()

    data class SingleInteger(
        val values: List<Int>,
        val initialValue: Int
    ) : ValueSelectionType<Int>()

    data class RangeSelection(
        val values: List<Int>,
        val initialRange: Pair<Int, Int>,
        val rangeFormatter: (Int, Int) -> String = { start, end -> "$start-$end" }
    ) : ValueSelectionType<String>()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> ValueSelectionBottomSheet(
    title: String,
    selectionType: ValueSelectionType<T>,
    sheetState: SheetState,
    onDismiss: () -> Unit,
    onConfirm: (T) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    
    GenericBottomSheet(
        title = title,
        onDismiss = onDismiss,
        sheetState = sheetState,
        maxHeightFraction = 0.8f
    ) {
        when (selectionType) {
            is ValueSelectionType.SingleFloat -> {
                SingleFloatContent(
                    values = selectionType.values,
                    initialValue = selectionType.initialValue,
                    formatter = selectionType.formatter,
                    listState = listState,
                    onConfirm = { onConfirm(it as T) }
                )
            }
            is ValueSelectionType.WeightSelection -> {
                WeightSelectionContent(
                    values = selectionType.values,
                    initialValue = selectionType.initialValue,
                    unit = selectionType.unit,
                    formatter = selectionType.formatter,
                    listState = listState,
                    onConfirm = { onConfirm(it as T) }
                )
            }
            is ValueSelectionType.SingleInteger -> {
                SingleIntegerContent(
                    values = selectionType.values,
                    initialValue = selectionType.initialValue,
                    listState = listState,
                    onConfirm = { onConfirm(it as T) }
                )
            }
            is ValueSelectionType.RangeSelection -> {
                RangeSelectionContent(
                    values = selectionType.values,
                    initialRange = selectionType.initialRange,
                    rangeFormatter = selectionType.rangeFormatter,
                    listState = listState,
                    onConfirm = { onConfirm(it as T) }
                )
            }
        }
    }
}

@Composable
private fun SingleFloatContent(
    values: List<Float>,
    initialValue: Float,
    formatter: (Float) -> String,
    listState: androidx.compose.foundation.lazy.LazyListState,
    onConfirm: (Float) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var selectedValue by remember { mutableStateOf(initialValue) }
    var manualInput by remember { mutableStateOf("") }

    // scroll to initial value when shown
    LaunchedEffect(Unit) {
        val index = values.indexOfFirst { abs(it - initialValue) < 0.01f }.coerceAtLeast(0)
        listState.scrollToItem(index)
    }

    // update selected value based on scroll position
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo }
            .collectLatest { visibleItems ->
                if (visibleItems.isNotEmpty()) {
                    val center = listState.layoutInfo.viewportEndOffset / 4
                    val closest = visibleItems.minByOrNull { abs((it.offset + it.size / 2) - center) }
                    closest?.let {
                        val idx = it.index
                        if (idx in values.indices) {
                            selectedValue = values[idx]
                        }
                    }
                }
            }
    }

    // scroll when user types a value present in the list
    LaunchedEffect(manualInput) {
        val typed = manualInput.toFloatOrNull()
        if (typed != null && values.contains(typed)) {
            val idx = values.indexOf(typed)
            selectedValue = typed
            coroutineScope.launch {
                listState.animateScrollToItem(idx)
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // scrollable list of values
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(top = 100.dp, bottom = 150.dp)
        ) {
            itemsIndexed(values) { index, value ->
                val isSelected = abs(value - selectedValue) < 0.01f
                val scale by animateFloatAsState(if (isSelected) 1.15f else 1f, label = "scale")
                
                Card(
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .fillMaxWidth(0.7f)
                        .height(56.dp)
                        .graphicsLayer {
                            scaleX = scale
                            scaleY = scale
                        }
                        .clickable {
                            if (isSelected) {
                                onConfirm(selectedValue)
                            } else {
                                coroutineScope.launch {
                                    listState.animateScrollToItem(index)
                                }
                            }
                        },
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = if (isSelected) 6.dp else 2.dp
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected) 
                            MaterialTheme.colorScheme.primaryContainer 
                        else 
                            MaterialTheme.colorScheme.surface
                    ),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = formatter(value),
                            style = if (isSelected) 
                                MaterialTheme.typography.headlineMedium.copy(
                                    fontWeight = FontWeight.Bold
                                ) 
                            else 
                                MaterialTheme.typography.titleLarge,
                            textAlign = TextAlign.Center,
                            color = if (isSelected) 
                                MaterialTheme.colorScheme.onPrimaryContainer 
                            else 
                                MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }

        // manual input field
        FormInputCard(
            title = "",
            value = manualInput,
            onValueChange = { newValue ->
                if (newValue.isEmpty() || newValue.matches(Regex("^\\d*\\.?\\d*$"))) {
                    manualInput = newValue
                }
            },
            placeholder = "enter value"
        )

        // confirm button
        Button(
            onClick = {
                val value = manualInput.toFloatOrNull()
                if (value != null) {
                    selectedValue = value
                    onConfirm(value)
                }
            },
            enabled = manualInput.isNotBlank(),
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(
                "confirm",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}

@Composable
private fun WeightSelectionContent(
    values: List<Float>,
    initialValue: Float,
    unit: String,
    formatter: (Float) -> String,
    listState: androidx.compose.foundation.lazy.LazyListState,
    onConfirm: (Float) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var selectedValue by remember { mutableStateOf(initialValue) }
    var manualInput by remember { mutableStateOf("") }

    // scroll to initial value when shown
    LaunchedEffect(Unit) {
        val index = values.indexOfFirst { abs(it - initialValue) < 0.0001f }.coerceAtLeast(0)
        listState.scrollToItem(index)
    }

    // update selected value based on scroll position
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo }
            .collectLatest { visibleItems ->
                if (visibleItems.isNotEmpty()) {
                    val center = listState.layoutInfo.viewportEndOffset / 4
                    val closest = visibleItems.minByOrNull { abs((it.offset + it.size / 2) - center) }
                    closest?.let {
                        val idx = it.index
                        if (idx in values.indices) {
                            selectedValue = values[idx]
                        }
                    }
                }
            }
    }

    // scroll when user types a value present in the list
    LaunchedEffect(manualInput) {
        val typed = manualInput.toFloatOrNull()
        if (typed != null && values.contains(typed)) {
            val idx = values.indexOf(typed)
            selectedValue = typed
            coroutineScope.launch {
                listState.animateScrollToItem(idx)
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // scrollable list of values
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(top = 120.dp, bottom = 200.dp)
        ) {
            itemsIndexed(values) { index, value ->
                val isSelected = abs(value - selectedValue) < 0.0001f
                val scale by animateFloatAsState(if (isSelected) 1.1f else 1f, label = "scale")
                
                Card(
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .fillMaxWidth(0.8f)
                        .height(60.dp)
                        .graphicsLayer {
                            scaleX = scale
                            scaleY = scale
                        }
                        .clickable {
                            if (isSelected) {
                                onConfirm(selectedValue)
                            } else {
                                coroutineScope.launch {
                                    listState.animateScrollToItem(index)
                                }
                            }
                        },
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = if (isSelected) 4.dp else 0.dp
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected) 
                            MaterialTheme.colorScheme.primaryContainer 
                        else 
                            MaterialTheme.colorScheme.surface
                    )
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${formatter(value)} $unit",
                            style = if (isSelected) 
                                MaterialTheme.typography.titleLarge 
                            else 
                                MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                            color = if (isSelected) 
                                MaterialTheme.colorScheme.onPrimaryContainer 
                            else 
                                MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }

        // manual input field
        FormInputCard(
            title = "",
            value = manualInput,
            onValueChange = { newValue ->
                if (newValue.isEmpty() || newValue.matches(Regex("^\\d*\\.?\\d*$"))) {
                    manualInput = newValue
                }
            },
            placeholder = "enter value ($unit)"
        )

        // confirm button
        Button(
            onClick = {
                val value = manualInput.toFloatOrNull()
                if (value != null) {
                    selectedValue = value
                    onConfirm(value)
                }
            },
            enabled = manualInput.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("confirm")
        }
    }
}

@Composable
private fun SingleIntegerContent(
    values: List<Int>,
    initialValue: Int,
    listState: androidx.compose.foundation.lazy.LazyListState,
    onConfirm: (Int) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var selectedValue by remember { mutableStateOf(initialValue) }
    var manualInput by remember { mutableStateOf("") }

    // scroll to initial value when shown
    LaunchedEffect(Unit) {
        val index = values.indexOf(initialValue).coerceAtLeast(0)
        listState.scrollToItem(index)
    }

    // update selected value based on scroll position
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo }
            .collectLatest { visibleItems ->
                if (visibleItems.isNotEmpty()) {
                    val center = listState.layoutInfo.viewportEndOffset / 4
                    val closest = visibleItems.minByOrNull { abs((it.offset + it.size / 2) - center) }
                    closest?.let {
                        val idx = it.index
                        if (idx in values.indices) {
                            selectedValue = values[idx]
                        }
                    }
                }
            }
    }

    // scroll when user types a value present in the list
    LaunchedEffect(manualInput) {
        val typed = manualInput.toIntOrNull()
        if (typed != null && values.contains(typed)) {
            val idx = values.indexOf(typed)
            selectedValue = typed
            coroutineScope.launch {
                listState.animateScrollToItem(idx)
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // scrollable list of values
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(top = 100.dp, bottom = 150.dp)
        ) {
            itemsIndexed(values) { index, value ->
                val isSelected = value == selectedValue
                val scale by animateFloatAsState(if (isSelected) 1.15f else 1f, label = "scale")
                
                Card(
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .fillMaxWidth(0.7f)
                        .height(56.dp)
                        .graphicsLayer {
                            scaleX = scale
                            scaleY = scale
                        }
                        .clickable {
                            if (isSelected) {
                                onConfirm(selectedValue)
                            } else {
                                coroutineScope.launch {
                                    listState.animateScrollToItem(index)
                                }
                            }
                        },
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = if (isSelected) 6.dp else 2.dp
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected) 
                            MaterialTheme.colorScheme.primaryContainer 
                        else 
                            MaterialTheme.colorScheme.surface
                    ),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = value.toString(),
                            style = if (isSelected) 
                                MaterialTheme.typography.headlineMedium.copy(
                                    fontWeight = FontWeight.Bold
                                ) 
                            else 
                                MaterialTheme.typography.titleLarge,
                            textAlign = TextAlign.Center,
                            color = if (isSelected) 
                                MaterialTheme.colorScheme.onPrimaryContainer 
                            else 
                                MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }

        // manual input field
        FormInputCard(
            title = "",
            value = manualInput,
            onValueChange = { newValue ->
                if (newValue.isEmpty() || newValue.matches(Regex("^\\d+$"))) {
                    manualInput = newValue
                }
            },
            placeholder = "enter number"
        )

        // confirm button
        Button(
            onClick = {
                val value = manualInput.toIntOrNull()
                if (value != null) {
                    selectedValue = value
                    onConfirm(value)
                }
            },
            enabled = manualInput.isNotBlank(),
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(
                "confirm",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}

@Composable
private fun RangeSelectionContent(
    values: List<Int>,
    initialRange: Pair<Int, Int>,
    rangeFormatter: (Int, Int) -> String,
    listState: androidx.compose.foundation.lazy.LazyListState,
    onConfirm: (String) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var startValue by remember { mutableStateOf(initialRange.first) }
    var endValue by remember { mutableStateOf(initialRange.second) }
    var isSelectingUpperBound by remember { mutableStateOf(false) }
    var manualInput by remember { mutableStateOf("") }

    // scroll to start value when shown
    LaunchedEffect(Unit) {
        val index = values.indexOf(startValue).coerceAtLeast(0)
        listState.scrollToItem(index)
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // current range display
        Surface(
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(
                text = rangeFormatter(startValue, endValue),
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }

        // scrollable list of values
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(top = 100.dp, bottom = 150.dp)
        ) {
            itemsIndexed(values) { index, value ->
                val isInRange = value in startValue..endValue
                val isStart = value == startValue
                val isEnd = value == endValue
                val isSelected = isStart || isEnd
                
                val scale by animateFloatAsState(if (isInRange) 1.15f else 1f, label = "scale")
                
                Card(
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .fillMaxWidth(0.7f)
                        .height(56.dp)
                        .graphicsLayer {
                            scaleX = scale
                            scaleY = scale
                        }
                        .clickable {
                            if (!isSelectingUpperBound) {
                                // first selection - set lower bound and start selecting upper bound
                                startValue = value
                                endValue = value
                                isSelectingUpperBound = true
                            } else {
                                // second selection - set upper bound and confirm
                                if (value < startValue) {
                                    // if selected value is less than start, swap them
                                    endValue = startValue
                                    startValue = value
                                } else {
                                    endValue = value
                                }
                                onConfirm(rangeFormatter(startValue, endValue))
                            }
                            
                            coroutineScope.launch {
                                listState.animateScrollToItem(index)
                            }
                        },
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = if (isInRange) 8.dp else 2.dp
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isInRange) 
                            MaterialTheme.colorScheme.primaryContainer 
                        else 
                            MaterialTheme.colorScheme.surface
                    ),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = value.toString(),
                            style = if (isInRange) 
                                MaterialTheme.typography.headlineMedium.copy(
                                    fontWeight = FontWeight.Bold
                                ) 
                            else 
                                MaterialTheme.typography.titleLarge,
                            textAlign = TextAlign.Center,
                            color = if (isInRange) 
                                MaterialTheme.colorScheme.onPrimaryContainer 
                            else 
                                MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }

        // manual input field
        FormInputCard(
            title = "",
            value = manualInput,
            onValueChange = { newValue ->
                if (newValue.isEmpty() || newValue.matches(Regex("^\\d*-?\\d*$"))) {
                    manualInput = newValue
                }
            },
            placeholder = "enter range (e.g., 8-12)"
        )

        // confirm button
        Button(
            onClick = {
                val range = manualInput.split("-").mapNotNull { it.trim().toIntOrNull() }
                if (range.size == 2 && range[0] <= range[1]) {
                    startValue = range[0]
                    endValue = range[1]
                    onConfirm(rangeFormatter(range[0], range[1]))
                }
            },
            enabled = manualInput.isNotBlank(),
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(
                "confirm",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
} 