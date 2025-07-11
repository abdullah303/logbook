package com.abdullah303.logbook.ui.create_split.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.abdullah303.logbook.data.model.Side
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyListState

// composable that displays left/right chips which can be reordered by drag
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SideOrderRow(
    sides: List<Side>,
    onReorder: (List<Side>) -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()
    val reorderableState = rememberReorderableLazyListState(lazyListState = listState) { from, to ->
        val fromIndex = sides.indexOfFirst { it.name == from.key }
        val toIndex = sides.indexOfFirst { it.name == to.key }
        if (fromIndex != -1 && toIndex != -1) {
            val updated = sides.toMutableList()
            val item = updated.removeAt(fromIndex)
            updated.add(toIndex, item)
            onReorder(updated)
        }
    }

    LazyRow(
        state = listState,
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 4.dp)
    ) {
        items(items = sides, key = { it.name }) { side ->
            ReorderableItem(state = reorderableState, key = side.name) {
                SideChip(
                    side = side,
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .draggableHandle()
                )
            }
        }
    }
}

@Composable
private fun SideChip(
    side: Side,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = MaterialTheme.shapes.extraSmall
    ) {
        Text(
            text = if (side == Side.LEFT) "left" else "right",
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Medium
        )
    }
} 