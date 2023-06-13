package com.globalcorp.taskman

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.globalcorp.taskman.databinding.FragmentStartBinding
import androidx.navigation.fragment.findNavController
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
        // user = auth.currentUser

        binding.startToMissionButton.setOnClickListener {
            //Firebase.analytics.logEvent("missions_button_clicked", null)
            findNavController().navigate(R.id.action_startFragment_to_missionsFragment)
        }

        binding.startToKittensFragmentButton.setOnClickListener {
            findNavController().navigate(R.id.action_startFragment_to_kittensFragment)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}