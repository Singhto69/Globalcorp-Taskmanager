package com.globalcorp.taskman

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.globalcorp.taskman.databinding.FragmentCreateNewMissionBinding
import com.globalcorp.taskman.ui.login.LoginViewModel
import com.globalcorp.taskman.ui.login.LoginViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.sql.Timestamp
import java.util.*


class CreateNewMissionFragment : Fragment() {
    private var _binding: FragmentCreateNewMissionBinding? = null
    private lateinit var createNewMissionViewModel: CreateNewMissionViewModel
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private var fireStoreDb = Firebase.firestore

    private val thisMission: HashMap<Any, Any> = HashMap()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateNewMissionBinding.inflate(inflater, container, false)


        auth = FirebaseAuth.getInstance()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createNewMissionViewModel = ViewModelProvider(this, CreateNewMissionViewModelFactory())
            .get(CreateNewMissionViewModel::class.java)

        binding.createNewMissionCreateMissionButton.setOnClickListener { createMission() }

        binding.createNewMissionTimeStartButton.setOnClickListener { timeStartOrStopInput(it) }

        binding.createNewMissionTimeStopButton.setOnClickListener { timeStartOrStopInput(it) }

    }

    private fun createMission() {
        val title: String = binding.createNewMissionTitleInput.text.toString()
        val description: String = binding.createNewMissionLocationInput.text.toString()
        val location: String = binding.createNewMissionDescriptionInput.text.toString()
        val type: String = binding.createNewMissionTypeInput.text.toString()

        if (title.isNotEmpty() && description.isNotEmpty() && location.isNotEmpty() && type.isNotEmpty() && thisMission.containsKey(
                "timestart"
            ) && thisMission.containsKey("timestop")
        ) {
            thisMission["title"] = title
            thisMission["description"] = description
            thisMission["address"] = location
            thisMission["type"] = type
            thisMission["status"] = 0
            val listTemplate: List<Any> = listOf()
            thisMission["users"] = listTemplate

            val timestart: Long = thisMission["timestart"] as Long
            val timestop: Long = thisMission["timestop"] as Long
            thisMission["timestart"] = com.google.firebase.Timestamp(Date(timestart))
            thisMission["timestop"] = com.google.firebase.Timestamp(Date(timestop))

            fireStoreDb.collection("missions").add(thisMission)
                .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
                .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }

        }


    }

    private fun timeStartOrStopInput(view: View) {
        when (view) {
            binding.createNewMissionTimeStartButton -> showDateTimePicker(key = "timestart")
            binding.createNewMissionTimeStopButton -> showDateTimePicker(key = "timestop")
        }
    }

    private fun showDateTimePicker(key: String) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog =
            DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
                val selectedTimestamp = getTimestamp(selectedYear, selectedMonth, selectedDay)
                showTimePicker(selectedTimestamp, key)
            }, year, month, dayOfMonth)

        datePickerDialog.show()
    }

    private fun showTimePicker(dateTimestamp: Long, key: String) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog =
            TimePickerDialog(requireContext(), { _, selectedHour, selectedMinute ->
                val selectedTimestamp =
                    addTimeToTimestamp(dateTimestamp, selectedHour, selectedMinute)
                thisMission[key] = selectedTimestamp
            }, hour, minute, false)

        timePickerDialog.show()
    }

    private fun getTimestamp(year: Int, month: Int, dayOfMonth: Int): Long {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth, 0, 0, 0)
        return calendar.timeInMillis
    }

    private fun addTimeToTimestamp(timestamp: Long, hour: Int, minute: Int): Long {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        return calendar.timeInMillis
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) = CreateNewMissionFragment().apply {

        }
    }
}