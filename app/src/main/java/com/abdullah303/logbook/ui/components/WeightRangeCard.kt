package com.abdullah303.logbook.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.abdullah303.logbook.data.model.WeightUnit

@Composable
fun WeightRangeCard(
    title: String,
    minValue: String,
    maxValue: String,
    incrementValue: String,
    weightUnit: WeightUnit,
    onMinClick: () -> Unit,
    onMaxClick: () -> Unit,
    onIncrementClick: () -> Unit,
    onWeightUnitChange: (WeightUnit) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        // header row with title and weight unit toggle
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                modifier = Modifier.weight(1f)
            )
            
            WeightUnitButtonGroup(
                selectedUnit = weightUnit,
                onUnitSelected = onWeightUnitChange
            )
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        
        // three columns for min, max, and increment
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            // min weight column
            FormSelectionCard(
                title = "min",
                value = if (minValue.isNotBlank()) "$minValue ${weightUnit.name.lowercase()}" else "select min...",
                onSelectionClick = onMinClick,
                modifier = Modifier.weight(1f)
            )
            
            Spacer(modifier = Modifier.width(8.dp))
            
            // max weight column  
            FormSelectionCard(
                title = "max",
                value = if (maxValue.isNotBlank()) "$maxValue ${weightUnit.name.lowercase()}" else "select max...",
                onSelectionClick = onMaxClick,
                modifier = Modifier.weight(1f)
            )
            
            Spacer(modifier = Modifier.width(8.dp))
            
            // increment column
            FormSelectionCard(
                title = "increment",
                value = if (incrementValue.isNotBlank()) "$incrementValue ${weightUnit.name.lowercase()}" else "select increment...",
                onSelectionClick = onIncrementClick,
                modifier = Modifier.weight(1f)
            )
        }
    }
} 