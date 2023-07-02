package com.globalcorp.taskman

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.globalcorp.taskman.databinding.FragmentCreateNewMissionBinding
import com.google.firebase.auth.FirebaseAuth


class CreateNewMissionFragment : Fragment() {
    private var _binding: FragmentCreateNewMissionBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateNewMissionBinding.inflate(inflater, container, false)

        auth = FirebaseAuth.getInstance()
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) = CreateNewMissionFragment().apply {

        }
    }
}