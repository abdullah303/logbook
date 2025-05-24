package com.abdullah303.logbook.core.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.Config
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.BitmapDrawable
import androidx.annotation.ColorInt
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.abdullah303.logbook.features.splits.data.DummyData
import com.abdullah303.logbook.features.splits.data.Split

/**
 * Fast muscle visualization using actual muscle diagram images with color overlays.
 */
object MuscleImageGenerator {

    private val availableMuscleGroups = setOf(
        "chest", "back", "biceps", "triceps", "quadriceps", "hamstrings", 
        "calves", "shoulders", "glutes", "forearms", "abs", "traps"
    )

    /**
     * Maps muscle names to resource identifiers.
     */
    private val muscleResourceMap = mapOf(
        "chest" to "muscles_chest",
        "back" to "muscles_back",
        "biceps" to "muscles_biceps",
        "triceps" to "muscles_triceps",
        "quadriceps" to "muscles_quadriceps",
        "hamstrings" to "muscles_hamstring",
        "calves" to "muscles_calfs",
        "shoulders" to "muscles_shoulders",
        "glutes" to "muscles_gluteus",
        "forearms" to "muscles_forearms",
        "abs" to "muscles_abs",
        "traps" to "muscles_neck" // Using neck as closest match for traps
    )

    /**
     * Maps exercise template muscle names to our standardized muscle names.
     */
    private fun normalizeMuscle(muscleName: String): String? {
        return when (muscleName.lowercase().trim()) {
            "chest", "upper chest" -> "chest"
            "triceps" -> "triceps"
            "shoulders", "front deltoids", "lateral deltoids", "rear deltoids" -> "shoulders"
            "back" -> "back"
            "biceps" -> "biceps"
            "quadriceps" -> "quadriceps"
            "hamstrings" -> "hamstrings"
            "glutes" -> "glutes"
            "calves" -> "calves"
            "forearms" -> "forearms"
            "lower back" -> "back"
            "traps", "rhomboids" -> "traps"
            "abs", "core" -> "abs"
            else -> null // Return null for unrecognized muscle names
        }
    }

    /**
     * Computes muscle usage frequency across all workouts in the split.
     */
    fun computeMuscleUsage(split: Split): Map<String, Int> {
        val counts = mutableMapOf<String, Int>().withDefault { 0 }

        split.workouts.forEach { workout ->
            workout.exercises.forEach { instance ->
                val template = DummyData.exerciseTemplates
                    .firstOrNull { it.id == instance.exerciseTemplateId }

                template?.let {
                    // Normalize primary muscle
                    normalizeMuscle(it.primaryMuscle)?.let { normalizedMuscle ->
                        counts[normalizedMuscle] = counts.getValue(normalizedMuscle) + instance.sets
                    }
                    
                    // Normalize secondary muscles
                    it.secondaryMuscles.forEach { secondary ->
                        normalizeMuscle(secondary)?.let { normalizedMuscle ->
                            counts[normalizedMuscle] = counts.getValue(normalizedMuscle) + (instance.sets / 2)
                        }
                    }
                }
            }
        }

        return counts
    }

    /**
     * Computes muscle usage frequency for a single workout.
     */
    fun computeWorkoutMuscleUsage(workout: com.abdullah303.logbook.features.splits.data.Workout): Map<String, Int> {
        val counts = mutableMapOf<String, Int>().withDefault { 0 }

        workout.exercises.forEach { instance ->
            val template = DummyData.exerciseTemplates
                .firstOrNull { it.id == instance.exerciseTemplateId }

            template?.let {
                // Normalize primary muscle
                normalizeMuscle(it.primaryMuscle)?.let { normalizedMuscle ->
                    counts[normalizedMuscle] = counts.getValue(normalizedMuscle) + instance.sets
                }
                
                // Normalize secondary muscles
                it.secondaryMuscles.forEach { secondary ->
                    normalizeMuscle(secondary)?.let { normalizedMuscle ->
                        counts[normalizedMuscle] = counts.getValue(normalizedMuscle) + (instance.sets / 2)
                    }
                }
            }
        }

        return counts
    }

    /**
     * Converts usage count to hex color string using a gradient of a single color.
     */
    fun getIntensityColorHex(count: Int, maxCount: Int, baseColor: String = "2196F3"): String {
        if (count <= 0 || maxCount <= 0) return "#00000000" // Transparent

        val ratio = (count.toFloat() / maxCount).coerceIn(0f, 1f)
        // Use intensity from 0.3 to 1.0 for better visibility
        val intensity = (0.3f + 0.7f * ratio).coerceIn(0f, 1f)
        val alpha = (intensity * 255).toInt().coerceIn(0, 255)

        return String.format("#%02X%s", alpha, baseColor)
    }

    /**
     * Converts usage count to hex color string with custom color.
     */
    fun getIntensityColorHexWithColor(count: Int, maxCount: Int, baseColorHex: String): String {
        if (count <= 0 || maxCount <= 0) return "#00000000" // Transparent

        val ratio = (count.toFloat() / maxCount).coerceIn(0f, 1f)
        // Use intensity from 0.3 to 1.0 for better visibility
        val intensity = (0.3f + 0.7f * ratio).coerceIn(0f, 1f)
        val alpha = (intensity * 255).toInt().coerceIn(0, 255)

        // Remove # if present and ensure we have just the hex color
        val cleanColor = baseColorHex.removePrefix("#")
        return String.format("#%02X%s", alpha, cleanColor)
    }

    /**
     * Generates a muscle heatmap bitmap based on workout split data.
     *
     * @param context Context to access resources.
     * @param split The workout split to analyze.
     * @param transparentBg Whether to use transparent background.
     * @return Composite Bitmap showing muscle usage intensity.
     * @throws IllegalArgumentException if inputs are invalid.
     */
    fun getMuscleHeatmapImage(
        context: Context,
        split: Split,
        transparentBg: Boolean = false
    ): Bitmap {
        val muscleUsage = computeMuscleUsage(split)
        val maxCount = muscleUsage.values.maxOrNull() ?: 1

        // Load base image
        val baseRes = if (transparentBg) "muscles_base_image_transparent" else "muscles_base_image"
        val baseBitmap = loadBitmapByName(context, baseRes)
            ?: throw IllegalArgumentException("Base image '$baseRes' not found.")
        val result = baseBitmap.copy(Config.ARGB_8888, true)
        val canvas = Canvas(result)

        // Overlay each muscle group with usage-based color
        muscleUsage.forEach { (muscleName, count) ->
            if (count > 0 && availableMuscleGroups.contains(muscleName)) {
                val overlayName = muscleResourceMap[muscleName]
                    ?: throw IllegalArgumentException("No resource mapping for muscle '$muscleName'.")
                
                val overlayBitmap = loadBitmapByName(context, overlayName)
                    ?: throw IllegalArgumentException("Overlay image '$overlayName' not found.")

                val colorHex = getIntensityColorHex(count, maxCount)
                val color = parseColor(colorHex)

                // Tint overlay
                val paint = android.graphics.Paint().apply {
                    colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN)
                }
                canvas.drawBitmap(overlayBitmap, 0f, 0f, paint)
            }
        }

        return result
    }

    /**
     * Generates a muscle heatmap bitmap based on a single workout.
     *
     * @param context Context to access resources.
     * @param workout The workout to analyze.
     * @param transparentBg Whether to use transparent background.
     * @return Composite Bitmap showing muscle usage intensity for the workout.
     * @throws IllegalArgumentException if inputs are invalid.
     */
    fun getWorkoutMuscleHeatmapImage(
        context: Context,
        workout: com.abdullah303.logbook.features.splits.data.Workout,
        transparentBg: Boolean = true
    ): Bitmap {
        val muscleUsage = computeWorkoutMuscleUsage(workout)
        val maxCount = muscleUsage.values.maxOrNull() ?: 1

        // Load base image
        val baseRes = if (transparentBg) "muscles_base_image_transparent" else "muscles_base_image"
        val baseBitmap = loadBitmapByName(context, baseRes)
            ?: throw IllegalArgumentException("Base image '$baseRes' not found.")
        val result = baseBitmap.copy(Config.ARGB_8888, true)
        val canvas = Canvas(result)

        // Overlay each muscle group with usage-based color
        muscleUsage.forEach { (muscleName, count) ->
            if (count > 0 && availableMuscleGroups.contains(muscleName)) {
                val overlayName = muscleResourceMap[muscleName]
                    ?: throw IllegalArgumentException("No resource mapping for muscle '$muscleName'.")
                
                val overlayBitmap = loadBitmapByName(context, overlayName)
                    ?: throw IllegalArgumentException("Overlay image '$overlayName' not found.")

                val colorHex = getIntensityColorHex(count, maxCount)
                val color = parseColor(colorHex)

                // Tint overlay
                val paint = android.graphics.Paint().apply {
                    colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN)
                }
                canvas.drawBitmap(overlayBitmap, 0f, 0f, paint)
            }
        }

        return result
    }

    /**
     * Generates a muscle heatmap bitmap based on a single workout with custom color.
     *
     * @param context Context to access resources.
     * @param workout The workout to analyze.
     * @param transparentBg Whether to use transparent background.
     * @param heatmapColor Hex color string for the gradient (without #).
     * @return Composite Bitmap showing muscle usage intensity for the workout.
     * @throws IllegalArgumentException if inputs are invalid.
     */
    fun getWorkoutMuscleHeatmapImageWithColor(
        context: Context,
        workout: com.abdullah303.logbook.features.splits.data.Workout,
        transparentBg: Boolean = true,
        heatmapColor: String = "2196F3"
    ): Bitmap {
        val muscleUsage = computeWorkoutMuscleUsage(workout)
        val maxCount = muscleUsage.values.maxOrNull() ?: 1

        // Load base image
        val baseRes = if (transparentBg) "muscles_base_image_transparent" else "muscles_base_image"
        val baseBitmap = loadBitmapByName(context, baseRes)
            ?: throw IllegalArgumentException("Base image '$baseRes' not found.")
        val result = baseBitmap.copy(Config.ARGB_8888, true)
        val canvas = Canvas(result)

        // Overlay each muscle group with usage-based color
        muscleUsage.forEach { (muscleName, count) ->
            if (count > 0 && availableMuscleGroups.contains(muscleName)) {
                val overlayName = muscleResourceMap[muscleName]
                    ?: throw IllegalArgumentException("No resource mapping for muscle '$muscleName'.")
                
                val overlayBitmap = loadBitmapByName(context, overlayName)
                    ?: throw IllegalArgumentException("Overlay image '$overlayName' not found.")

                val colorHex = getIntensityColorHexWithColor(count, maxCount, heatmapColor)
                val color = parseColor(colorHex)

                // Tint overlay
                val paint = android.graphics.Paint().apply {
                    colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN)
                }
                canvas.drawBitmap(overlayBitmap, 0f, 0f, paint)
            }
        }

        return result
    }

    /**
     * Generates a composite bitmap highlighting specified muscle groups with a single color.
     *
     * @param context Context to access resources.
     * @param muscleGroups List of muscle group identifiers.
     * @param colorStr Hex color string (e.g. "#FF0000" or "FF0000").
     * @param transparentBg Whether to use transparent background.
     * @return Composite Bitmap.
     * @throws IllegalArgumentException if inputs are invalid.
     */
    fun getMuscleImage(
        context: Context,
        muscleGroups: List<String>,
        colorStr: String,
        transparentBg: Boolean = false
    ): Bitmap {
        if (muscleGroups.isEmpty()) {
            throw IllegalArgumentException("No muscle groups specified.")
        }

        val color = parseColor(colorStr)

        // Load base image
        val baseRes = if (transparentBg) "muscles_base_image_transparent" else "muscles_base_image"
        val baseBitmap = loadBitmapByName(context, baseRes)
            ?: throw IllegalArgumentException("Base image '$baseRes' not found.")
        val result = baseBitmap.copy(Config.ARGB_8888, true)
        val canvas = Canvas(result)

        // Overlay each muscle group
        muscleGroups.map { it.trim().lowercase() }.forEach { group ->
            if (!availableMuscleGroups.contains(group)) {
                throw IllegalArgumentException(
                    "Unknown muscle group '$group'. Available: ${availableMuscleGroups.joinToString()}."
                )
            }
            
            val overlayName = muscleResourceMap[group]
                ?: throw IllegalArgumentException("No resource mapping for muscle '$group'.")
            
            val overlayBitmap = loadBitmapByName(context, overlayName)
                ?: throw IllegalArgumentException("Overlay image '$overlayName' not found.")

            // Tint overlay
            val paint = android.graphics.Paint().apply {
                colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN)
            }
            canvas.drawBitmap(overlayBitmap, 0f, 0f, paint)
        }

        return result
    }

    @ColorInt
    private fun parseColor(colorStr: String): Int {
        val str = if (colorStr.startsWith("#")) colorStr else "#$colorStr"
        return try {
            Color.parseColor(str)
        } catch (e: IllegalArgumentException) {
            throw IllegalArgumentException("Invalid color '$colorStr'. Use hex format.")
        }
    }

    private fun loadBitmapByName(context: Context, name: String): Bitmap? {
        val resId = context.resources.getIdentifier(name, "drawable", context.packageName)
        if (resId == 0) return null
        val drawable = ContextCompat.getDrawable(context, resId) as? BitmapDrawable
        return drawable?.bitmap
    }
}

/**
 * Composable that displays muscle heatmap using the new bitmap-based approach.
 */
@Composable
fun MuscleHeatmapImage(
    split: Split,
    modifier: Modifier = Modifier,
    transparentBg: Boolean = false
) {
    val context = LocalContext.current
    val bitmap = remember(split, transparentBg) {
        try {
            val muscleUsage = MuscleImageGenerator.computeMuscleUsage(split)
            println("DEBUG: Muscle usage computed: $muscleUsage")
            
            val result = MuscleImageGenerator.getMuscleHeatmapImage(
                context = context,
                split = split,
                transparentBg = transparentBg
            )
            println("DEBUG: Bitmap generated successfully: ${result.width}x${result.height}")
            result
        } catch (e: Exception) {
            println("ERROR: Failed to generate muscle heatmap: ${e.message}")
            e.printStackTrace()
            null
        }
    }

    bitmap?.let {
        Image(
            bitmap = it.asImageBitmap(),
            contentDescription = "Muscle heatmap showing workout intensity",
            modifier = modifier,
            contentScale = ContentScale.Fit
        )
    } ?: run {
        // Show a placeholder or error state when bitmap is null
        println("DEBUG: No bitmap to display")
    }
}

/**
 * Composable that displays muscle heatmap for a single workout with transparent background.
 */
@Composable
fun WorkoutMuscleHeatmapImage(
    workout: com.abdullah303.logbook.features.splits.data.Workout,
    modifier: Modifier = Modifier,
    transparentBg: Boolean = true,
    heatmapColor: String = "2196F3" // Default blue color
) {
    val context = LocalContext.current
    val bitmap = remember(workout, transparentBg, heatmapColor) {
        try {
            println("DEBUG: Computing muscle usage for workout: ${workout.name}")
            println("DEBUG: Workout has ${workout.exercises.size} exercises")
            
            workout.exercises.forEach { exercise ->
                val template = DummyData.exerciseTemplates.firstOrNull { it.id == exercise.exerciseTemplateId }
                template?.let {
                    println("DEBUG: Exercise: ${it.name}, Primary: ${it.primaryMuscle}, Secondary: ${it.secondaryMuscles}, Sets: ${exercise.sets}")
                }
            }
            
            val muscleUsage = MuscleImageGenerator.computeWorkoutMuscleUsage(workout)
            println("DEBUG: Workout muscle usage computed: $muscleUsage")
            
            if (muscleUsage.isEmpty()) {
                println("WARNING: No muscle usage found - check muscle name mapping")
                return@remember null
            }
            
            val result = MuscleImageGenerator.getWorkoutMuscleHeatmapImageWithColor(
                context = context,
                workout = workout,
                transparentBg = transparentBg,
                heatmapColor = heatmapColor
            )
            println("DEBUG: Workout bitmap generated successfully: ${result.width}x${result.height}")
            result
        } catch (e: Exception) {
            println("ERROR: Failed to generate workout muscle heatmap: ${e.message}")
            e.printStackTrace()
            null
        }
    }

    bitmap?.let {
        Image(
            bitmap = it.asImageBitmap(),
            contentDescription = "Muscle heatmap showing workout intensity",
            modifier = modifier,
            contentScale = ContentScale.Fit
        )
    } ?: run {
        // Show a placeholder or error state when bitmap is null
        println("DEBUG: No workout bitmap to display - showing empty space")
    }
}
