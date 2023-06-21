package com.globalcorp.taskman

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.globalcorp.taskman.databinding.FragmentAdminBinding
import com.globalcorp.taskman.databinding.FragmentMissionsBinding


class AdminFragment : Fragment() {
    private var _binding: FragmentAdminBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAdminBinding.inflate(inflater, container, false)

        binding.adminToCreateMissionButton.setOnClickListener() {
            findNavController().navigate(R.id.action_adminFragment_to_createNewMissionFragment)
        }

        binding.adminToAssignMissionButton.setOnClickListener() {
            findNavController().navigate(R.id.action_adminFragment_to_createNewMissionFragment)
        }

        return binding.root
    }

}