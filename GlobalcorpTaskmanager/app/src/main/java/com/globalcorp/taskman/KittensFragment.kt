package com.globalcorp.taskman

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.globalcorp.taskman.databinding.FragmentKittensBinding

class KittensFragment : Fragment() {

    private val viewModel: KittensViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentKittensBinding.inflate(inflater)


        //val binding = GridViewItemBinding.inflate(inflater)
        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        //binding.lifecycleOwner = this

        // Giving the binding access to the OverviewViewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.status
    }



}

