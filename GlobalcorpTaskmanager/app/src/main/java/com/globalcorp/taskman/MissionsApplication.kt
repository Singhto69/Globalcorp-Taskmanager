package com.globalcorp.taskman

import android.app.Application
import com.globalcorp.taskman.database.MissionsRoomDatabase

class MissionsApplication : Application() {
    val database: MissionsRoomDatabase by lazy { MissionsRoomDatabase.getDatabase(this) }
}




