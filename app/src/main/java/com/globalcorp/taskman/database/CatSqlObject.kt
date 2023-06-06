package com.globalcorp.taskman.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item")
data class CatSqlObject(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "extId")
    val extId: Int,
    @ColumnInfo(name = "name")
    val imgUrl: String,
    @ColumnInfo(name = "price")
    val width: Int,
    @ColumnInfo(name = "stock")
    val height: Int
)