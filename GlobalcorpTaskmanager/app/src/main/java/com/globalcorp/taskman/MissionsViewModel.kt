package com.globalcorp.taskman

import androidx.lifecycle.*
import com.globalcorp.taskman.database.MissionsDao
import com.globalcorp.taskman.database.MissionsSqlObject
import com.globalcorp.taskman.models.Mission
import com.globalcorp.taskman.network.MissionsApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MissionsViewModel(private val missionsDao: MissionsDao) : ViewModel() {
    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status

    private val _missions = MutableLiveData<List<Mission>>()
    val missions: LiveData<List<Mission>> = _missions


    init {
        updateDb()


        // 1. API get missions
        // 2. Double check and update db
        // 3. update the display list


    }
    fun updateDb() {
        apiSyncMissions()

        /*val specificItem = _missions.value?.get(0)
        addNewMission(specificItem)*/
    }

    fun displayAssignedMissions(){

    }

    fun displayFreeMissions(){

    }

    private fun getAllMissions(): Flow<List<MissionsSqlObject>>? {
        var allMissions: Flow<List<MissionsSqlObject>>? = null
        viewModelScope.launch {
            allMissions = missionsDao.getMissions()
        }
        return allMissions
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

    /* private suspend fun zeroCheck() {
        viewmodel.getListener()?.collect { myClass->
            //do something here
            return@collect
        }
        withContext(Dispatchers.Main) { return@withContext updateUI() }
        //the code should flow downwards as usual
    }*/


    private fun apiSyncMissions() {
        viewModelScope.launch {
            try {
                val listResult = MissionsApiService.MissionsApi.retrofitService.getMissions()
                _status.value = "Success: ${listResult} retrieved"

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