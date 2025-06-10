package com.abdullah303.logbook.core.utils

enum class Muscle(val displayName: String, val resourceName: String) {
    ABDUCTORS("abductors", "muscles_abductors"),
    ABS("abs", "muscles_abs"),
    ADDUCTORS("adductors", "muscles_adductors"),
    BICEPS("biceps", "muscles_biceps"),
    CALVES("calves", "muscles_calves"),
    CHEST("chest", "muscles_chest"),
    FOREARMS("forearms", "muscles_forearms"),
    FRONT_DELTS("front delts", "muscles_front_delts"),
    GLUTES("glutes", "muscles_glutes"),
    GRIP("grip", "muscles_grip"),
    HAMS("hams", "muscles_hams"),
    LATS("lats", "muscles_lats"),
    LOWER_BACK("lower back", "muscles_lower_back"),
    NECK("neck", "muscles_neck"),
    OBLIQUES("obliques", "muscles_obliques"),
    QUADS("quads", "muscles_quads"),
    REAR_DELTS("rear delts", "muscles_rear_delts"),
    SIDE_DELTS("side delts", "muscles_side_delts"),
    TRICEPS("triceps", "muscles_triceps"),
    UPPER_BACK("upper back", "muscles_upper_back");

    companion object {
        /**
         * Maps exercise template muscle names to our standardized muscle enum.
         */
        fun fromString(muscleName: String): Muscle? {
            return when (muscleName.lowercase().trim()) {
                "abductors" -> ABDUCTORS
                "abs", "core" -> ABS
                "adductors" -> ADDUCTORS
                "biceps" -> BICEPS
                "calves" -> CALVES
                "chest", "upper chest" -> CHEST
                "forearms" -> FOREARMS
                "front delts", "front deltoids" -> FRONT_DELTS
                "glutes" -> GLUTES
                "grip" -> GRIP
                "hams", "hamstrings" -> HAMS
                "lats" -> LATS
                "lower back" -> LOWER_BACK
                "neck" -> NECK
                "obliques" -> OBLIQUES
                "quads", "quadriceps" -> QUADS
                "rear delts", "rear deltoids" -> REAR_DELTS
                "side delts", "lateral deltoids" -> SIDE_DELTS
                "triceps" -> TRICEPS
                "upper back", "traps", "rhomboids" -> UPPER_BACK
                else -> null // Return null for unrecognized muscle names
            }
        }
    }
} 