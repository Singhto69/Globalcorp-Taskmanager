package com.globalcorp.taskman

import android.content.ContentValues.TAG
import android.icu.text.DateFormat.getDateInstance
import android.icu.text.SimpleDateFormat
import android.util.Log
import androidx.lifecycle.*
import com.globalcorp.taskman.database.MissionsDao
import com.globalcorp.taskman.database.MissionsSqlObject
import com.globalcorp.taskman.models.Mission
import com.globalcorp.taskman.network.MissionsApiService
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow

import androidx.room.TypeConverter
import com.google.firebase.Timestamp
import java.util.*


class MissionsViewModel(private val missionsDao: MissionsDao) : ViewModel() {
    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status

    private val dataBase: FirebaseFirestore = FirebaseFirestore.getInstance()

    init {
        refresh()
    }

    /*fun setup() {
        Handler(Looper.getMainLooper()).postDelayed({
            refresh()
            setup()
        }, 5000)
    }*/


    // 1. API get missions 2. update db 3. grab missions from db 4. Assign to missions for livedata display
    fun refresh() {
        apiDbSync()

    }

    fun displayAssignedMissions() {

    }

    fun displayFreeMissions() {

    }

    fun allMissions(): Flow<List<MissionsSqlObject>> = missionsDao.getAll()

    fun deleteAll() {
        viewModelScope.launch {
            missionsDao.wipe()
        }
    }


    private fun insertMission(mission: MissionsSqlObject) {
        viewModelScope.launch {
            missionsDao.insert(mission)
        }

    }

    private fun getNewMissionEntry(
        id: String,
        title: String,
        location: String,
        date: String,
        description: String,
        timeStart: String,
        timeStop: String,
        userId: String
    ): MissionsSqlObject {
        return MissionsSqlObject(
            id = id,
            title = title,
            location = location,
            date = date,
            description = description,
            timeStart = timeStart,
            timeStop = timeStop,
            userId = userId
        )
    }

    fun addNewMission(mission: Mission?) {

        //id:Int, title: String, location: String, description: String,
        //                      date: String, timeStart: String,
        //                      timeStop: String, userId: Int
        if (mission != null) {
            val newItem = getNewMissionEntry(
                mission.id,
                mission.title,
                mission.location,
                mission.description,
                mission.date,
                mission.timeStart,
                mission.timeStop,
                mission.userId
            )
            insertMission(newItem)
        }
    }

    fun deleteMission(missionId: Int) {
        viewModelScope.launch {
            missionsDao.deleteMissionById(missionId)
        }

    }

    private fun apiDbSync() {
        val dateFormat = getDateInstance()
        val calendar: Calendar = Calendar.getInstance()

        viewModelScope.launch {
            try {
                dataBase.collection("missions")
                    .whereEqualTo("status", 0)
                    .get()
                    .addOnSuccessListener {
                            querySnapshot ->
                        for (document in querySnapshot) {
                            //val data = document.data
                            insertMission(getNewMissionEntry(
                                id = document.id,
                                title = document.get("title") as String,
                                location = document.get("address") as String,
                                description = document.get("description") as String,
                                date = dateFormat
                                    .format(
                                        (document.get("timestart") as Timestamp)
                                            .toDate()
                                    ) as String,
                                timeStart = dateFormat
                                    .format(
                                        (document.get("timestart") as Timestamp)
                                            .toDate()
                                    ) as String,
                                timeStop = dateFormat
                                    .format(
                                        (document.get("timestop") as Timestamp)
                                            .toDate()
                                    ) as String,
                                userId = document.get("users") as String
                            ))

                            // Do something with the document data
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.d(TAG, "Error getting documents: ", exception)
                    }

                /*val listResult = MissionsApiService.MissionsApi.retrofitService.getMissions()
                _status.value = "Success"
                for (mission in listResult) {
                    addNewMission(mission)
                }*/
                allMissions()
            } catch (e: Exception) {
                _status.value = "Failure"
                allMissions()
            }

        }
    }


}

class MissionsViewModelFactory(private val missionsDao: MissionsDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MissionsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") return MissionsViewModel(missionsDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}