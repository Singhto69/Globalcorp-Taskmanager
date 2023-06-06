package com.globalcorp.taskman.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MissionsSqlObject::class], version = 1, exportSchema = false)
abstract class MissionsRoomDatabase : RoomDatabase() {

    abstract fun missionsDao(): MissionsDao

    companion object {
        @Volatile
        private var INSTANCE: MissionsRoomDatabase? = null
        fun getDatabase(context: Context): MissionsRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MissionsRoomDatabase::class.java,
                    "missions_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}