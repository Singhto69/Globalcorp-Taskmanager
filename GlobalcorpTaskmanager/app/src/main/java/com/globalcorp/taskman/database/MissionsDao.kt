package com.globalcorp.taskman.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MissionsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(missionsObject: MissionsSqlObject)

    @Update
    suspend fun update(missionsObject: MissionsSqlObject)

    @Delete
    suspend fun delete(missionsObject: MissionsSqlObject)


    @Query("SELECT * from item WHERE id = :id")
    fun getMission(id: Int): Flow<MissionsSqlObject>

    @Query("SELECT * from item ORDER BY id ASC")
    fun getMissions(): Flow<List<MissionsSqlObject>>


}