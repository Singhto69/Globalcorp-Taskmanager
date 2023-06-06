package com.globalcorp.taskman.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CatSqlObject::class], version = 1, exportSchema = false)
abstract class CatRoomDatabase : RoomDatabase() {

    abstract fun itemDao(): CatDao

    companion object {
        @Volatile
        private var INSTANCE: CatRoomDatabase? = null
        fun getDatabase(context: Context): CatRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CatRoomDatabase::class.java,
                    "item_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}