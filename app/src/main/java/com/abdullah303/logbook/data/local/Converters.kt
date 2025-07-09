package com.abdullah303.logbook.data.local.converter

import androidx.room.TypeConverter
import com.abdullah303.logbook.data.model.*
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Converters {
    
    // BigDecimal conversions
    @TypeConverter
    fun fromBigDecimal(value: BigDecimal?): String? {
        return value?.toString()
    }
    
    @TypeConverter
    fun toBigDecimal(value: String?): BigDecimal? {
        return value?.let { BigDecimal(it) }
    }
    
    // LocalDateTime conversions
    @TypeConverter
    fun fromLocalDateTime(value: LocalDateTime?): String? {
        return value?.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    }
    
    @TypeConverter
    fun toLocalDateTime(value: String?): LocalDateTime? {
        return value?.let { LocalDateTime.parse(it, DateTimeFormatter.ISO_LOCAL_DATE_TIME) }
    }
    
    // WeightUnit enum conversions
    @TypeConverter
    fun fromWeightUnit(value: WeightUnit?): String? {
        return value?.name
    }
    
    @TypeConverter
    fun toWeightUnit(value: String?): WeightUnit? {
        return value?.let { WeightUnit.valueOf(it) }
    }
    
    // EquipmentType enum conversions
    @TypeConverter
    fun fromEquipmentType(value: EquipmentType?): String? {
        return value?.name
    }
    
    @TypeConverter
    fun toEquipmentType(value: String?): EquipmentType? {
        return value?.let { EquipmentType.valueOf(it) }
    }
    
    // Muscles enum conversions
    @TypeConverter
    fun fromMuscles(value: Muscles?): String? {
        return value?.name
    }
    
    @TypeConverter
    fun toMuscles(value: String?): Muscles? {
        return value?.let { Muscles.valueOf(it) }
    }
    
    // ResistanceMachineType enum conversions
    @TypeConverter
    fun fromResistanceMachineType(value: ResistanceMachineType?): String? {
        return value?.name
    }
    
    @TypeConverter
    fun toResistanceMachineType(value: String?): ResistanceMachineType? {
        return value?.let { ResistanceMachineType.valueOf(it) }
    }
    
    // LoggedWorkoutStatus enum conversions
    @TypeConverter
    fun fromLoggedWorkoutStatus(value: LoggedWorkoutStatus?): String? {
        return value?.name
    }
    
    @TypeConverter
    fun toLoggedWorkoutStatus(value: String?): LoggedWorkoutStatus? {
        return value?.let { LoggedWorkoutStatus.valueOf(it) }
    }
    
    // List<BigDecimal> conversions (for future use)
    @TypeConverter
    fun fromBigDecimalList(value: List<BigDecimal>?): String? {
        return value?.joinToString(",") { it.toString() }
    }
    
    @TypeConverter
    fun toBigDecimalList(value: String?): List<BigDecimal>? {
        return value?.split(",")?.mapNotNull { 
            try {
                BigDecimal(it.trim())
            } catch (e: NumberFormatException) {
                null
            }
        }
    }
    
    // List<Muscles> conversions (for future use)
    @TypeConverter
    fun fromMusclesList(value: List<Muscles>?): String? {
        return value?.joinToString(",") { it.name }
    }
    
    @TypeConverter
    fun toMusclesList(value: String?): List<Muscles>? {
        return value?.split(",")?.mapNotNull { 
            try {
                Muscles.valueOf(it.trim())
            } catch (e: IllegalArgumentException) {
                null
            }
        }
    }
    
    // List<String> conversions
    @TypeConverter
    fun fromStringList(value: List<String>?): String? {
        return value?.joinToString("|||") // using triple pipe as separator to avoid conflicts with normal text
    }
    
    @TypeConverter
    fun toStringList(value: String?): List<String>? {
        return if (value.isNullOrEmpty()) {
            null
        } else {
            value.split("|||").map { it.trim() }
        }
    }
} 