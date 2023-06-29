package com.globalcorp.taskman

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar

import androidx.navigation.fragment.NavHostFragment
import com.globalcorp.taskman.databinding.FragmentStartBinding
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.firebase.auth.FirebaseAuth


class StartFragment : Fragment() {
    private var _binding: FragmentStartBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStartBinding.inflate(inflater, container, false)

        auth = FirebaseAuth.getInstance()
        //var user = auth.currentUser

        binding.startToMissionsImageButton.setOnClickListener {
            //Firebase.analytics.logEvent("missions_button_clicked", null)
            findNavController().navigate(R.id.action_startFragment_to_missionsFragment)
        }

        binding.startToKittensImageButton.setOnClickListener {
            findNavController().navigate(R.id.action_startFragment_to_kittensFragment)
        }

        binding.startToAdminImageButton.setOnClickListener {
            findNavController().navigate(R.id.action_startFragment_to_adminFragment)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}