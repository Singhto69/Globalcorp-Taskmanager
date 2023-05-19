package com.example.busschedule.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.busschedule.database.ScheduleDao

/* ViewModel class BusScheduleViewModel is meant to be lifecycle aware,
 it should be instantiated by an object that can respond to lifecycle events.
  If you instantiate it directly in one of your fragments,
  then your fragment object will have to handle everything, including all the memory management,
  which is beyond the scope of what your app's code should do. Instead, you can create a class,
  called a factory, that will instantiate view model objects for you.
 */

class BusScheduleViewModelFactory(
    private val scheduleDao: ScheduleDao
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BusScheduleViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BusScheduleViewModel(scheduleDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }


}