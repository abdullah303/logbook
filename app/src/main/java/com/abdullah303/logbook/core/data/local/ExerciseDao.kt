package com.abdullah303.logbook.core.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {
    
    @Query(
        "SELECT exercises.*, equipment.name AS equipmentName " +
                "FROM exercises " +
                "INNER JOIN equipment ON exercises.equipmentId = equipment.id " +
                "ORDER BY exercises.name ASC"
    )
    fun getAllExercises(): Flow<List<ExerciseWithEquipment>>
    
    @Query(
        "SELECT exercises.*, equipment.name AS equipmentName " +
                "FROM exercises " +
                "INNER JOIN equipment ON exercises.equipmentId = equipment.id " +
                "WHERE exercises.id = :id"
    )
    suspend fun getExerciseById(id: String): ExerciseWithEquipment?
    
    @Query(
        "SELECT exercises.*, equipment.name AS equipmentName " +
                "FROM exercises " +
                "INNER JOIN equipment ON exercises.equipmentId = equipment.id " +
                "WHERE exercises.name LIKE '%' || :searchQuery || '%' " +
                "ORDER BY exercises.name ASC"
    )
    fun searchExercises(searchQuery: String): Flow<List<ExerciseWithEquipment>>
    
    @Query(
        "SELECT exercises.*, equipment.name AS equipmentName " +
                "FROM exercises " +
                "INNER JOIN equipment ON exercises.equipmentId = equipment.id " +
                "WHERE exercises.primaryMuscle = :muscle " +
                "ORDER BY exercises.name ASC"
    )
    fun getExercisesByPrimaryMuscle(muscle: String): Flow<List<ExerciseWithEquipment>>
    
    @Query(
        "SELECT exercises.*, equipment.name AS equipmentName " +
                "FROM exercises " +
                "INNER JOIN equipment ON exercises.equipmentId = equipment.id " +
                "WHERE exercises.equipmentId = :equipmentId " +
                "ORDER BY exercises.name ASC"
    )
    fun getExercisesByEquipmentId(equipmentId: String): Flow<List<ExerciseWithEquipment>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercise(exercise: ExerciseEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercises(exercises: List<ExerciseEntity>)
    
    @Update
    suspend fun updateExercise(exercise: ExerciseEntity)
    
    @Delete
    suspend fun deleteExercise(exercise: ExerciseEntity)
    
    @Query("DELETE FROM exercises WHERE id = :id")
    suspend fun deleteExerciseById(id: String)
    
    @Query("DELETE FROM exercises")
    suspend fun deleteAllExercises()
    
    @Query("SELECT COUNT(*) FROM exercises")
    suspend fun getExerciseCount(): Int
} 