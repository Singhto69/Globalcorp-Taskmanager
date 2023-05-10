package com.globalcorp.taskman

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.globalcorp.taskman.databinding.FragmentKittensBinding
import com.globalcorp.taskman.databinding.FragmentStartBinding
import com.globalcorp.taskman.utils.ImageLoader

class KittensFragment : Fragment() {
    private val viewModel: KittensViewModel by viewModels()

    private var _binding: FragmentKittensBinding? = null
    private val binding get() = _binding!!

    private var imageLoader : ImageLoader? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentKittensBinding.inflate(inflater)
        /*
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(SEARCH_PREFIX + movie.id))
            startActivity(intent)

         */

        imageLoader = view?.let { ImageLoader(it.context) }
        viewModel.imageLoader = imageLoader


        //val binding = GridViewItemBinding.inflate(inflater)
        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        //binding.lifecycleOwner = this

        // Giving the binding access to the OverviewViewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }




}

