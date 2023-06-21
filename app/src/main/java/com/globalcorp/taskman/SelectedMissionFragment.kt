package com.globalcorp.taskman

import android.content.ClipData
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.globalcorp.taskman.databinding.FragmentSelectedMissionBinding
import com.globalcorp.taskman.models.Mission


class SelectedMissionFragment : Fragment() {
    private var _binding: FragmentSelectedMissionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectedMissionBinding.inflate(inflater)
        val mission = SelectedMissionFragmentArgs.fromBundle(requireArguments()).mission
        binding.mission = mission


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    companion object {
        private const val ARG_MISSION = "mission"

        @JvmStatic
        fun newInstance(mission: Mission): SelectedMissionFragment {
            val fragment = SelectedMissionFragment()
            val args = Bundle()
            args.putParcelable(ARG_MISSION, mission)
            fragment.arguments = args
            return fragment
        }
    }

}