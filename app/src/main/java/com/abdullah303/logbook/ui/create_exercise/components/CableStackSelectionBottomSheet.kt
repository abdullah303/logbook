package com.abdullah303.logbook.ui.create_exercise.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.abdullah303.logbook.data.local.entity.CableStackInfo
import com.abdullah303.logbook.data.local.entity.Equipment
import com.abdullah303.logbook.ui.create_equipment.create_cable_stack.CreateCableStackBottomSheet
import kotlinx.coroutines.launch

// data class to combine equipment and cable stack info
data class CableStackConfiguration(
    val equipment: Equipment,
    val cableStackInfo: CableStackInfo
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CableStackSelectionBottomSheet(
    cableStackConfigurations: List<CableStackConfiguration>,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {},
    onSelectCableStack: (CableStackConfiguration) -> Unit = {},
    onCableStackCreated: (CableStackConfiguration) -> Unit = {},
    sheetState: SheetState,
    maxHeightFraction: Float = 0.75f
) {
    var showCreateCableStack by remember { mutableStateOf(false) }
    val createCableStackSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    
    // convert cable stack configurations to unified equipment configurations
    val unifiedConfigurations = cableStackConfigurations.map { cableStackConfig ->
        cableStackConfig.equipment.withCableStackInfo(cableStackConfig.cableStackInfo)
    }

    EquipmentSelectionBottomSheet(
        title = "Cable Stack Configurations",
        configurations = unifiedConfigurations,
        modifier = modifier,
        onDismiss = onDismiss,
        onSelectConfiguration = { unifiedConfig ->
            // convert back to cable stack configuration
            val originalCableStackConfig = cableStackConfigurations.find { 
                it.equipment.id == unifiedConfig.equipment.id 
            }
            originalCableStackConfig?.let { onSelectCableStack(it) }
        },
        onNavigateToCreateConfiguration = {
            showCreateCableStack = true
        },
        sheetState = sheetState,
        maxHeightFraction = maxHeightFraction
    )
    
    // show create cable stack bottom sheet when needed
    if (showCreateCableStack) {
        LaunchedEffect(showCreateCableStack) {
            if (showCreateCableStack) {
                createCableStackSheetState.show()
            }
        }
        CreateCableStackBottomSheet(
            onDismiss = {
                showCreateCableStack = false
                scope.launch {
                    createCableStackSheetState.hide()
                }
            },
            onCableStackCreated = { cableStackConfiguration ->
                onCableStackCreated(cableStackConfiguration)
                showCreateCableStack = false
                scope.launch {
                    createCableStackSheetState.hide()
                }
            },
            sheetState = createCableStackSheetState,
            maxHeightFraction = maxHeightFraction
        )
    }
} 