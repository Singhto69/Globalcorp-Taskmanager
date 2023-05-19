package com.globalcorp.taskman

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.globalcorp.taskman.database.missiondb
import com.globalcorp.taskman.databinding.FragmentMissionsBinding
import com.ltu.m7019e.v23.themoviedb.adapter.MovieListAdapter
import kotlinx.coroutines.NonDisposableHandle.parent


class MissionsFragment : Fragment() {
    private var _binding: FragmentMissionsBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //val context = getActivity()
        //val myDataSet = missiondb().loadlist()
        //val recyclerView = binding.recyclerView1
        //recyclerView.adapter =  MissionAdapter(context.context,myDataSet)
        //recyclerView.setHasFixedSize(true)


        //val myDataSet = missiondb().loadlist()
        //binding.recyclerView1.adapter = MissionAdapter(view.context, myDataSet)
        //binding.recyclerView1.setHasFixedSize(true)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMissionsBinding.inflate(inflater, container, false)

        val myDataSet = missiondb().loadlist()
        binding.recyclerView1.adapter = MissionAdapter(myDataSet)


        //return inflater.inflate(R.layout.fragment_missions, container, false)
        //return view
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //val missionAdapter = context?.let { MissionAdapter(it, myDataSet) }!!


        //val layoutManager = LinearLayoutManager(context)

        // requireActivity()


        //recyclerView.layoutManager = layoutManager

        //recyclerView.setHasFixedSize(true)


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