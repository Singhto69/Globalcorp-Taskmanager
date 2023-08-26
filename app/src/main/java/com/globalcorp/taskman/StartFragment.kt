package com.globalcorp.taskman

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.coroutineScope

import androidx.navigation.fragment.NavHostFragment
import com.globalcorp.taskman.databinding.FragmentStartBinding
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.firebase.auth.FirebaseAuth
import com.globalcorp.taskman.utils.meow


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
            meow(requireContext(), lifecycle.coroutineScope)
            findNavController().navigate(R.id.action_startFragment_to_missionsFragment)

        }

        binding.startToKittensImageButton.setOnClickListener {
            meow(requireContext(), lifecycle.coroutineScope)
            findNavController().navigate(R.id.action_startFragment_to_kittensFragment)

        }

        binding.startToAdminImageButton.setOnClickListener {
            meow(requireContext(), lifecycle.coroutineScope)
            findNavController().navigate(R.id.action_startFragment_to_adminFragment)

        }

        val activity = activity as AppCompatActivity
        activity.supportActionBar?.title = "Start"

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}