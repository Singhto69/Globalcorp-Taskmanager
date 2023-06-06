package com.globalcorp.taskman.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CatDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(catObject: CatSqlObject)

    @Update
    suspend fun update(catObject: CatSqlObject)

    @Delete
    suspend fun delete(catObject: CatSqlObject)


    @Query("SELECT * from item WHERE id = :id")
    fun getItem(id: Int): Flow<CatSqlObject>

    @Query("SELECT * from item ORDER BY name ASC")
    fun getItems(): Flow<List<CatSqlObject>>


}