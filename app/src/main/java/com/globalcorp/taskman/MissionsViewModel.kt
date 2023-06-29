package com.globalcorp.taskman

import android.content.ContentValues.TAG
import android.icu.text.DateFormat.getDateInstance
import android.util.Log
import androidx.lifecycle.*
import com.globalcorp.taskman.database.MissionsDao
import com.globalcorp.taskman.models.Mission
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.*

import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.firestore.ktx.memoryCacheSettings
import com.google.firebase.firestore.ktx.persistentCacheSettings
import java.util.*

class MissionsViewModel(private val missionsDao: MissionsDao) : ViewModel() {
    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status

    private val _allMissions = MutableLiveData<List<Mission>>()
    val allMissions: LiveData<List<Mission>> = _allMissions

    private val dataBase: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _missionsTabState: MutableLiveData<MissionsTabState> = MutableLiveData()
    val missionsTabState: LiveData<MissionsTabState> = _missionsTabState

    //var missionsTabState: MissionsTabState = MissionsTabState.AVAILABLE


    init {
        _missionsTabState.value = MissionsTabState.AVAILABLE
        val settings = firestoreSettings {
            // Use memory cache
            setLocalCacheSettings(memoryCacheSettings {})
            // Use persistent disk cache (default)
            setLocalCacheSettings(persistentCacheSettings {})
        }
        dataBase.firestoreSettings = settings
        refresh()
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
                    listResult.add(
                        Mission(
                            id = document.id,
                            title = document.get("title") as String,
                            location = document.get("address") as String,
                            description = document.get("description") as String,
                            date = dateFormat.format(
                                (document.get("timestart") as Timestamp).toDate()
                            ) as String,
                            timeStart = dateFormat.format(
                                (document.get("timestart") as Timestamp).toDate()
                            ) as String,
                            timeStop = dateFormat.format(
                                (document.get("timestop") as Timestamp).toDate()
                            ) as String,
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

//dataBase.collection("missions").whereEqualTo("status", 0).get()