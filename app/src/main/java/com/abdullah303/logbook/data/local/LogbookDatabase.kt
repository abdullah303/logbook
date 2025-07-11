package com.abdullah303.logbook.data.local.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import com.abdullah303.logbook.data.local.dao.UserDao
import com.abdullah303.logbook.data.local.dao.SettingsDao
import com.abdullah303.logbook.data.local.dao.DumbbellRangeDao
import com.abdullah303.logbook.data.local.dao.EquipmentDao
import com.abdullah303.logbook.data.local.dao.ExerciseDao
import com.abdullah303.logbook.data.local.dao.CableStackInfoDao
import com.abdullah303.logbook.data.local.dao.BarbellInfoDao
import com.abdullah303.logbook.data.local.dao.SmithMachineInfoDao
import com.abdullah303.logbook.data.local.dao.ResistanceMachineInfoDao
import com.abdullah303.logbook.data.local.dao.PinLoadedInfoDao
import com.abdullah303.logbook.data.local.dao.PlateLoadedInfoDao
import com.abdullah303.logbook.data.local.dao.SplitsDao
import com.abdullah303.logbook.data.local.dao.LoggedSplitsDao
import com.abdullah303.logbook.data.local.dao.WorkoutDao
import com.abdullah303.logbook.data.local.dao.SupersetGroupDao
import com.abdullah303.logbook.data.local.dao.WorkoutExerciseDao
import com.abdullah303.logbook.data.local.dao.WorkoutExerciseSnapshotDao
import com.abdullah303.logbook.data.local.dao.LoggedWorkoutDao
import com.abdullah303.logbook.data.local.dao.LoggedSetDao
import com.abdullah303.logbook.data.local.dao.ExerciseNotesDao
import com.abdullah303.logbook.data.local.entity.User
import com.abdullah303.logbook.data.local.entity.Settings
import com.abdullah303.logbook.data.local.entity.DumbbellRange
import com.abdullah303.logbook.data.local.entity.Equipment
import com.abdullah303.logbook.data.local.entity.Exercise
import com.abdullah303.logbook.data.local.entity.CableStackInfo
import com.abdullah303.logbook.data.local.entity.BarbellInfo
import com.abdullah303.logbook.data.local.entity.SmithMachineInfo
import com.abdullah303.logbook.data.local.entity.ResistanceMachineInfo
import com.abdullah303.logbook.data.local.entity.PinLoadedInfo
import com.abdullah303.logbook.data.local.entity.PlateLoadedInfo
import com.abdullah303.logbook.data.local.entity.Splits
import com.abdullah303.logbook.data.local.entity.LoggedSplits
import com.abdullah303.logbook.data.local.entity.Workout
import com.abdullah303.logbook.data.local.entity.SupersetGroup
import com.abdullah303.logbook.data.local.entity.WorkoutExercise
import com.abdullah303.logbook.data.local.entity.WorkoutExerciseSnapshot
import com.abdullah303.logbook.data.local.entity.LoggedWorkout
import com.abdullah303.logbook.data.local.entity.LoggedSet
import com.abdullah303.logbook.data.local.entity.ExerciseNotes
import com.abdullah303.logbook.data.local.converter.Converters

@Database(
    entities = [User::class, Settings::class, DumbbellRange::class, Equipment::class, Exercise::class, CableStackInfo::class, BarbellInfo::class, SmithMachineInfo::class, ResistanceMachineInfo::class, PinLoadedInfo::class, PlateLoadedInfo::class, Splits::class, LoggedSplits::class, Workout::class, SupersetGroup::class, WorkoutExercise::class, WorkoutExerciseSnapshot::class, LoggedWorkout::class, LoggedSet::class, ExerciseNotes::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class LogbookDatabase : RoomDatabase() {
    
    abstract fun userDao(): UserDao
    abstract fun settingsDao(): SettingsDao
    abstract fun dumbbellRangeDao(): DumbbellRangeDao
    abstract fun equipmentDao(): EquipmentDao
    abstract fun exerciseDao(): ExerciseDao
    abstract fun cableStackInfoDao(): CableStackInfoDao
    abstract fun barbellInfoDao(): BarbellInfoDao
    abstract fun smithMachineInfoDao(): SmithMachineInfoDao
    abstract fun resistanceMachineInfoDao(): ResistanceMachineInfoDao
    abstract fun pinLoadedInfoDao(): PinLoadedInfoDao
    abstract fun plateLoadedInfoDao(): PlateLoadedInfoDao
    abstract fun splitsDao(): SplitsDao
    abstract fun loggedSplitsDao(): LoggedSplitsDao
    abstract fun workoutDao(): WorkoutDao
    abstract fun supersetGroupDao(): SupersetGroupDao
    abstract fun workoutExerciseDao(): WorkoutExerciseDao
    abstract fun workoutExerciseSnapshotDao(): WorkoutExerciseSnapshotDao
    abstract fun loggedWorkoutDao(): LoggedWorkoutDao
    abstract fun loggedSetDao(): LoggedSetDao
    abstract fun exerciseNotesDao(): ExerciseNotesDao
    
    companion object {
        @Volatile
        private var INSTANCE: LogbookDatabase? = null
        
        fun getDatabase(context: Context): LogbookDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LogbookDatabase::class.java,
                    "logbook_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
} 