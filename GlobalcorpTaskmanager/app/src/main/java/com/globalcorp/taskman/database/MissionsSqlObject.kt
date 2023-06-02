package com.globalcorp.taskman.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item")
data class MissionsSqlObject(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "location")
    val location: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "date")
    val date: String,
    @ColumnInfo(name = "timeStart")
    val timeStart: String,
    @ColumnInfo(name = "timeStop")
    val timeStop: String,
    @ColumnInfo(name = "userId")
    val userId: Int
)





