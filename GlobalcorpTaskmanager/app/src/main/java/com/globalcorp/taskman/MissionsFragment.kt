package com.globalcorp.taskman


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.globalcorp.taskman.databinding.FragmentMissionsBinding



class MissionsFragment : Fragment() {
    private var _binding: FragmentMissionsBinding? = null
    private val binding get() = _binding!!

    private var missionAdapter : MissionAdapter? = null

    //private val viewModel: MissionsViewModel by viewModels()

    private val viewModel: MissionsViewModel by activityViewModels {
        MissionsViewModelFactory(
            (activity?.application as MissionsApplication).database
                .missionsDao()

        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMissionsBinding.inflate(inflater, container, false)
        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        binding.xmlViewModel = viewModel


        missionAdapter = MissionAdapter()
        binding.recyclerViewMissions.adapter = missionAdapter



        /*
        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel

        binding.photosGrid.adapter = PhotoGridAdapter()*/

        viewModel.missions.observe(viewLifecycleOwner) {
            it?.let { missionAdapter!!.submitList(it) }
             }



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MissionsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MissionsFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}