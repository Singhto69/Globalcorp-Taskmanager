package com.globalcorp.taskman

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.globalcorp.taskman.databinding.FragmentStartBinding
import androidx.navigation.fragment.findNavController


class StartFragment : Fragment() {
    private var _binding: FragmentStartBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStartBinding.inflate(inflater, container, false)


        binding.startToMissionButton.setOnClickListener {
            navStartToMissions()
            // android:onClick="@{() -> StartFragment.navStartToMissions()}" NOT WORKING
        }


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navStartToMissions() {
        findNavController().navigate(R.id.action_startFragment_to_missionsFragment)
        //  android:onClick="@{() -> StartFragment.navStartToMissions()}"
        //  FOR WHAT THIS NOT WORKING BLYAT
    }
}