package com.globalcorp.taskman.database

import androidx.room.*
import com.globalcorp.taskman.models.Mission
import kotlinx.coroutines.flow.Flow

@Dao
interface MissionsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(missionsObject: MissionsSqlObject)

    @Update
    suspend fun update(missionsObject: MissionsSqlObject)

    @Delete
    suspend fun delete(missionsObject: MissionsSqlObject)


    @Query("SELECT * FROM item ORDER BY id ASC")
    fun getAll(): Flow<List<MissionsSqlObject>>
    @Query("SELECT * from item WHERE id = :id")
    fun getMission(id: Int): Flow<MissionsSqlObject>

    @Query("SELECT * from item ORDER BY id ASC")
    fun getMissions(): Flow<List<MissionsSqlObject>>

    @Query("DELETE FROM item WHERE id=:missionId")
    fun deleteMissionById(missionId : Int)

    @Query("DELETE FROM item")
    fun wipe()


}