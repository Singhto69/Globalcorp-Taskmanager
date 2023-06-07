package com.globalcorp.taskman

import androidx.lifecycle.*
import com.globalcorp.taskman.database.MissionsDao
import com.globalcorp.taskman.database.MissionsSqlObject
import com.globalcorp.taskman.models.Mission
import com.globalcorp.taskman.network.MissionsApiService
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope



class MissionsViewModel(private val missionsDao: MissionsDao) : ViewModel() {
    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status

    private val _missions = MutableLiveData<List<Mission>>()
    val missions: LiveData<List<Mission>> = _missions

    private val _allMissions = MutableLiveData<List<MissionsSqlObject>>()
    val allMissions: LiveData<List<MissionsSqlObject>> = _allMissions

    init {
        refresh()

    }
    // 1. API get missions
    // 2. update db
    // 3. grab missions from db
    // 4. Assign to missions for livedata display

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
        if (mission != null) {
            val newItem =
                getNewMissionEntry(
                    mission.id, mission.title, mission.location, mission.description,
                    mission.date, mission.timeStart, mission.timeStop, mission.userId
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
        viewModelScope.launch {
            try {
                val listResult = MissionsApiService.MissionsApi.retrofitService.getMissions()
                _status.value = "Success"
                for (mission in listResult) {
                    addNewMission(mission)
                }
                //_missions.value = listResult
                //getAllMissions()
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
            @Suppress("UNCHECKED_CAST")
            return MissionsViewModel(missionsDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}

// Code for update db in apisyncmission
// if listresult = 0 then do nothing

/*for (mission in listResult) {
    val missionCheck = missionsDao.getMission(mission.id)
    var missionCount = 0
    missionCheck.collect { list ->
        missionCount = missionCheck.count()
    }
    withContext(Dispatchers.Main){
        if (missionCount > 0) {
            missionsDao.update(
                getNewMissionEntry(
                    mission.id, mission.title, mission.location, mission.description,
                    mission.date, mission.timeStart, mission.timeStop, mission.userId
                )
            )
        } else {
            addNewMission(mission)
        }
    }

}*/