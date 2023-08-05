package com.globalcorp.taskman

import android.content.ContentValues.TAG
import android.content.Context
import android.icu.text.DateFormat.getDateInstance
import android.media.MediaPlayer
import android.util.Log
import androidx.lifecycle.*
import com.globalcorp.taskman.database.MissionsDao
import com.globalcorp.taskman.models.Mission
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.*

import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.firestore.ktx.memoryCacheSettings
import com.google.firebase.firestore.ktx.persistentCacheSettings
import java.util.*
import kotlin.random.Random

class MissionsViewModel(private val missionsDao: MissionsDao) : ViewModel() {
    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status

    private val dataBase: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _allMissions = MutableLiveData<List<Mission>>()
    val allMissions: LiveData<List<Mission>> = _allMissions

    private val _latestMission = MutableLiveData<Mission?>()
    val latestMission: LiveData<Mission?> = _latestMission

    private var previousMissions: List<Mission>? = null
    private lateinit var registration: ListenerRegistration
    private var firstInit = true

    private val _missionsTabState: MutableLiveData<MissionsTabState> = MutableLiveData()
    val missionsTabState: LiveData<MissionsTabState> = _missionsTabState


    init {
        _missionsTabState.value = MissionsTabState.AVAILABLE
        refresh()
        startListeningForMissions()
    }

    private fun startListeningForMissions() {
        val newMissionsRegistrationQuery = auth.currentUser?.let {
            dataBase.collection("missions").whereArrayContains("users", auth.currentUser!!.uid)
                .whereEqualTo("status", 0)
        }

        registration = newMissionsRegistrationQuery!!.addSnapshotListener { snapshot, error ->
            if (error != null) {
                Log.w(TAG, "Listen failed.", error)
                return@addSnapshotListener
            }

            val currentMissions = snapshot?.documents?.map { it.toObject(Mission::class.java)!! }

            if (firstInit) {
                previousMissions = currentMissions
                firstInit = false
                return@addSnapshotListener
            }

            val previousSize = previousMissions?.size ?: 0
            val currentSize = currentMissions?.size ?: 0

            if (currentSize != previousSize) {
                if (currentSize > previousSize) {
                    val newMission = currentMissions?.last()
                    _latestMission.value = newMission
                    databaseSync()

                } else {
                    _latestMission.value = null
                }
            }
            previousMissions = currentMissions
        }
    }

    fun refresh() {
        databaseSync()
    }

    private fun databaseSync() {
        val dateFormat = getDateInstance()
        val calendar: Calendar = Calendar.getInstance()
        val listResult = mutableListOf<Mission>()

        val status: Int = when (_missionsTabState.value) {
            MissionsTabState.AVAILABLE -> 0
            MissionsTabState.ACCEPTED -> 1
            MissionsTabState.FINISHED -> 2
            else -> {
                return
            }
        }


        val collectionRef = dataBase.collection("missions")
        val query = auth.currentUser?.let {
            collectionRef.whereArrayContains("users", auth.currentUser!!.uid)
                .whereEqualTo("status", status)
        }

        query!!.get().addOnSuccessListener { querySnapshot ->
            if (querySnapshot != null) {
                for (document in querySnapshot) {
                    val timestartDate = (document.get("timestart") as Timestamp).toDate()
                    val timestopDate = (document.get("timestop") as Timestamp).toDate()

                    val calendarStart = Calendar.getInstance().apply { time = timestartDate }
                    val calendarStop = Calendar.getInstance().apply { time = timestopDate }

                    val timestartHour = calendarStart.get(Calendar.HOUR_OF_DAY)
                    val timestopHour = calendarStop.get(Calendar.HOUR_OF_DAY)

                    val timestartMinute = calendarStart.get(Calendar.MINUTE)
                    val timestopMinute = calendarStop.get(Calendar.MINUTE)

                    val timeStart = String.format("%02d:%02d", timestartHour, timestartMinute)
                    val timeStop = String.format("%02d:%02d", timestopHour, timestopMinute)


                    listResult.add(
                        Mission(
                            id = document.id,
                            title = document.get("title") as String,
                            type = document.get("type") as String,
                            location = document.get("address") as String,
                            description = document.get("description") as String,
                            date = dateFormat.format(
                                (document.get("timestart") as Timestamp).toDate()
                            ) as String,
                            timeStart = timeStart,
                            timeStop = timeStop,
                            //userId = document.get("users") as String? <-- Array now
                            userId = auth.currentUser!!.uid,
                            status = ((document.get("status") as Any) as Long).toInt()

                        )
                    )
                }
            }
            _allMissions.value = listResult
        }.addOnFailureListener { exception ->
            Log.d(TAG, "Error getting documents: ", exception)
        }
    }

    fun meow(context: Context) {
        viewModelScope.launch {
            val rand: Int = Random.nextInt(0, 9)
            var catPath: Int = 0
            when (rand) {
                0 -> catPath = R.raw.cat_meow_0
                1 -> catPath = R.raw.cat_meow_1
                2 -> catPath = R.raw.cat_meow_2
                3 -> catPath = R.raw.cat_meow_3
                4 -> catPath = R.raw.cat_meow_4
                5 -> catPath = R.raw.cat_meow_5
                6 -> catPath = R.raw.cat_meow_6
                7 -> catPath = R.raw.cat_meow_7
                8 -> catPath = R.raw.cat_meow_8
                9 -> catPath = R.raw.cat_meow_9
            }
            var mediaPlayer: MediaPlayer? = MediaPlayer.create(context, catPath)
            mediaPlayer?.start()
            delay(5000)
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }


    fun setStateAvailable() {
        _missionsTabState.value = MissionsTabState.AVAILABLE
        refresh()
    }

    fun setStateAccepted() {
        _missionsTabState.value = MissionsTabState.ACCEPTED
        refresh()
    }

    fun setStateFinished() {
        _missionsTabState.value = MissionsTabState.FINISHED
        refresh()
    }

    override fun onCleared() {
        super.onCleared()
        registration.remove()
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

enum class MissionsTabState {
    AVAILABLE, ACCEPTED, FINISHED
}