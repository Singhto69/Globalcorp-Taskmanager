package com.globalcorp.taskman


import android.content.ClipData
import androidx.lifecycle.*
import com.globalcorp.taskman.database.MissionsDao
import com.globalcorp.taskman.database.MissionsSqlObject
import com.globalcorp.taskman.models.Mission
import com.globalcorp.taskman.network.MissionsApiService
import kotlinx.coroutines.launch


class MissionsViewModel(private val missionsDao: MissionsDao) : ViewModel() {
    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status

    private val _missions = MutableLiveData<List<Mission>>()
    val missions: LiveData<List<Mission>> = _missions


    init {
        getMissions()

    }

    fun updateDb() {
        val specificItem = _missions.value?.get(0)
        addNewMission(specificItem)
    }

    private fun insertMission(mission: MissionsSqlObject) {
        viewModelScope.launch {
            missionsDao.insert(mission)
        }
    }

    private fun getNewMissionEntry(
        id: Int, title: String, location: String, description: String,
        date: String, timeStart: String,
        timeStop: String, userId: Int
    ): MissionsSqlObject {
        return MissionsSqlObject(
            id = id,
            title = title,
            location = location,
            description = description,
            date = date,
            timeStart = timeStart,
            timeStop = timeStop,
            userId = userId
        )
    }

    fun addNewMission(mission: Mission?) {

        //id:Int, title: String, location: String, description: String,
        //                      date: String, timeStart: String,
        //                      timeStop: String, userId: Int
        if (mission != null){
            val newItem =
                getNewMissionEntry(mission.id, mission.title, mission.location, mission.description,
                    mission.date, mission.timeStart, mission.timeStop, mission.userId)
            insertMission(newItem)
        }

    }


    private fun getMissions() {
        viewModelScope.launch {
            try {
                val listResult = MissionsApiService.MissionsApi.retrofitService.getMissions()
                _status.value = "Success: ${listResult} retrieved"
                _missions.value = listResult


            } catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
            }
        }
    }
}

class MissionsViewModelFactory(private val missionsDao: MissionsDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MissionsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MissionsViewModel(missionsDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}