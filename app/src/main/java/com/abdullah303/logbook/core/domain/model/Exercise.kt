package com.abdullah303.logbook.core.domain.model

data class Exercise(
    val id: String = "",
    val name: String = "",
    val equipment: String = "",
    val equipmentId: String = "",
    val primaryMuscle: String = "",
    val auxiliaryMuscles: String = "",
    val bodyweightContribution: String = ""
) 