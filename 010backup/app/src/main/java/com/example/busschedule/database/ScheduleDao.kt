package com.example.busschedule.database

import androidx.room.Dao
import androidx.room.Query

/*
The DAO is where you would include functions for reading and manipulating data.
Calling a function on the DAO is the equivalent of performing a SQL command on the database.
In-fact, DAO functions like the ones you'll define in this app,
often specify a SQL command so you can specify exactly what you want the function to do
 */

@Dao
interface ScheduleDao {
    @Query("SELECT * FROM schedule ORDER BY arrival_time ASC")
    fun getAll(): List<Schedule>

    /* You can reference Kotlin values from the query by preceding it with a colon
     (:) (e.g. :stopName from the function parameter). */
    @Query("SELECT * FROM schedule WHERE stop_name = :stopName ORDER BY arrival_time ASC")
    fun getByStopName(stopName: String): List<Schedule>

}