package com.abdullah303.logbook.di

import android.content.Context
import com.abdullah303.logbook.data.local.db.LogbookDatabase
import com.abdullah303.logbook.data.local.dao.*
import com.abdullah303.logbook.data.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): LogbookDatabase {
        return LogbookDatabase.getDatabase(context)
    }

    // provide all DAOs
    @Provides
    fun provideUserDao(database: LogbookDatabase): UserDao = database.userDao()

    @Provides
    fun provideSettingsDao(database: LogbookDatabase): SettingsDao = database.settingsDao()

    @Provides
    fun provideDumbbellRangeDao(database: LogbookDatabase): DumbbellRangeDao = database.dumbbellRangeDao()

    @Provides
    fun provideEquipmentDao(database: LogbookDatabase): EquipmentDao = database.equipmentDao()

    @Provides
    fun provideExerciseDao(database: LogbookDatabase): ExerciseDao = database.exerciseDao()

    @Provides
    fun provideCableStackInfoDao(database: LogbookDatabase): CableStackInfoDao = database.cableStackInfoDao()

    @Provides
    fun provideBarbellInfoDao(database: LogbookDatabase): BarbellInfoDao = database.barbellInfoDao()

    @Provides
    fun provideSmithMachineInfoDao(database: LogbookDatabase): SmithMachineInfoDao = database.smithMachineInfoDao()

    @Provides
    fun provideResistanceMachineInfoDao(database: LogbookDatabase): ResistanceMachineInfoDao = database.resistanceMachineInfoDao()

    @Provides
    fun providePinLoadedInfoDao(database: LogbookDatabase): PinLoadedInfoDao = database.pinLoadedInfoDao()

    @Provides
    fun providePlateLoadedInfoDao(database: LogbookDatabase): PlateLoadedInfoDao = database.plateLoadedInfoDao()

    @Provides
    fun provideSplitsDao(database: LogbookDatabase): SplitsDao = database.splitsDao()

    @Provides
    fun provideLoggedSplitsDao(database: LogbookDatabase): LoggedSplitsDao = database.loggedSplitsDao()

    @Provides
    fun provideWorkoutDao(database: LogbookDatabase): WorkoutDao = database.workoutDao()

    @Provides
    fun provideSupersetGroupDao(database: LogbookDatabase): SupersetGroupDao = database.supersetGroupDao()

    @Provides
    fun provideWorkoutExerciseDao(database: LogbookDatabase): WorkoutExerciseDao = database.workoutExerciseDao()

    @Provides
    fun provideWorkoutExerciseSnapshotDao(database: LogbookDatabase): WorkoutExerciseSnapshotDao = database.workoutExerciseSnapshotDao()

    @Provides
    fun provideLoggedWorkoutDao(database: LogbookDatabase): LoggedWorkoutDao = database.loggedWorkoutDao()

    @Provides
    fun provideLoggedSetDao(database: LogbookDatabase): LoggedSetDao = database.loggedSetDao()

    @Provides
    fun provideExerciseNotesDao(database: LogbookDatabase): ExerciseNotesDao = database.exerciseNotesDao()

    // provide all repositories
    @Provides
    @Singleton
    fun provideUserRepository(userDao: UserDao): UserRepository = UserRepositoryImpl(userDao)

    @Provides
    @Singleton
    fun provideSettingsRepository(settingsDao: SettingsDao): SettingsRepository = SettingsRepositoryImpl(settingsDao)

    @Provides
    @Singleton
    fun provideDumbbellRangeRepository(dumbbellRangeDao: DumbbellRangeDao): DumbbellRangeRepository = DumbbellRangeRepositoryImpl(dumbbellRangeDao)

    @Provides
    @Singleton
    fun provideEquipmentRepository(equipmentDao: EquipmentDao): EquipmentRepository = EquipmentRepositoryImpl(equipmentDao)

    @Provides
    @Singleton
    fun provideExerciseRepository(exerciseDao: ExerciseDao): ExerciseRepository = ExerciseRepositoryImpl(exerciseDao)

    @Provides
    @Singleton
    fun provideCableStackInfoRepository(cableStackInfoDao: CableStackInfoDao): CableStackInfoRepository = CableStackInfoRepositoryImpl(cableStackInfoDao)

    @Provides
    @Singleton
    fun provideBarbellInfoRepository(barbellInfoDao: BarbellInfoDao): BarbellInfoRepository = BarbellInfoRepositoryImpl(barbellInfoDao)

    @Provides
    @Singleton
    fun provideSmithMachineInfoRepository(smithMachineInfoDao: SmithMachineInfoDao): SmithMachineInfoRepository = SmithMachineInfoRepositoryImpl(smithMachineInfoDao)

    @Provides
    @Singleton
    fun provideResistanceMachineInfoRepository(resistanceMachineInfoDao: ResistanceMachineInfoDao): ResistanceMachineInfoRepository = ResistanceMachineInfoRepositoryImpl(resistanceMachineInfoDao)

    @Provides
    @Singleton
    fun providePinLoadedInfoRepository(pinLoadedInfoDao: PinLoadedInfoDao): PinLoadedInfoRepository = PinLoadedInfoRepositoryImpl(pinLoadedInfoDao)

    @Provides
    @Singleton
    fun providePlateLoadedInfoRepository(plateLoadedInfoDao: PlateLoadedInfoDao): PlateLoadedInfoRepository = PlateLoadedInfoRepositoryImpl(plateLoadedInfoDao)

    @Provides
    @Singleton
    fun provideSplitsRepository(splitsDao: SplitsDao): SplitsRepository = SplitsRepositoryImpl(splitsDao)

    @Provides
    @Singleton
    fun provideLoggedSplitsRepository(loggedSplitsDao: LoggedSplitsDao): LoggedSplitsRepository = LoggedSplitsRepositoryImpl(loggedSplitsDao)

    @Provides
    @Singleton
    fun provideWorkoutRepository(workoutDao: WorkoutDao): WorkoutRepository = WorkoutRepositoryImpl(workoutDao)

    @Provides
    @Singleton
    fun provideSupersetGroupRepository(supersetGroupDao: SupersetGroupDao): SupersetGroupRepository = SupersetGroupRepositoryImpl(supersetGroupDao)

    @Provides
    @Singleton
    fun provideWorkoutExerciseRepository(workoutExerciseDao: WorkoutExerciseDao): WorkoutExerciseRepository = WorkoutExerciseRepositoryImpl(workoutExerciseDao)

    @Provides
    @Singleton
    fun provideWorkoutExerciseSnapshotRepository(workoutExerciseSnapshotDao: WorkoutExerciseSnapshotDao): WorkoutExerciseSnapshotRepository = WorkoutExerciseSnapshotRepositoryImpl(workoutExerciseSnapshotDao)

    @Provides
    @Singleton
    fun provideLoggedWorkoutRepository(loggedWorkoutDao: LoggedWorkoutDao): LoggedWorkoutRepository = LoggedWorkoutRepositoryImpl(loggedWorkoutDao)

    @Provides
    @Singleton
    fun provideLoggedSetRepository(loggedSetDao: LoggedSetDao): LoggedSetRepository = LoggedSetRepositoryImpl(loggedSetDao)

    @Provides
    @Singleton
    fun provideExerciseNotesRepository(exerciseNotesDao: ExerciseNotesDao): ExerciseNotesRepository = ExerciseNotesRepositoryImpl(exerciseNotesDao)
} 