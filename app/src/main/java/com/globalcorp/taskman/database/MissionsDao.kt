package com.globalcorp.taskman.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MissionsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(missionsObject: com.globalcorp.taskman.database.Mission)

    @Update
    suspend fun update(missionsObject: com.globalcorp.taskman.database.Mission)

    @Delete
    suspend fun delete(missionsObject: com.globalcorp.taskman.database.Mission)

    /* Remember Dao functions that have a suspend modifier must not
     return a deferred/async type (kotlinx.coroutines.flow.Flow).
     */
    @Query("SELECT * FROM item ORDER BY id ASC")
    fun getAll(): Flow<List<com.globalcorp.taskman.database.Mission>>
    @Query("SELECT * from item WHERE id = :id")
    fun getMission(id: Int): Flow<com.globalcorp.taskman.database.Mission>

    @Query("SELECT * from item ORDER BY id ASC")
    fun getMissions(): Flow<List<com.globalcorp.taskman.database.Mission>>

    @Query("DELETE FROM item WHERE id=:missionId")
    fun deleteMissionById(missionId : Int)

    @Query("DELETE FROM item")
    suspend fun wipe()


}