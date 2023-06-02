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
import com.globalcorp.taskman.network.CatObject
import com.globalcorp.taskman.utils.ImageLoader

class KittensFragment : Fragment() {
    private val viewModel: KittensViewModel by viewModels()

    private var _binding: FragmentKittensBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentKittensBinding.inflate(inflater)
        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this
        /*
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(SEARCH_PREFIX + movie.id))
        startActivity(intent)
         */

        binding.xmlViewModel = viewModel

        /*viewModel.images.observe(viewLifecycleOwner) {
            viewModel.images.value?.let { it1 -> upDateCat(it1) }
        }*/

        //val binding = GridViewItemBinding.inflate(inflater)
        // Giving the binding access to the OverviewViewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    fun upDateCat(images: List<CatObject>) {
        //binding.catObject = images[0]

        //binding.imageLoader.loadImage(binding.catObject.imgUrl, binding.catImageView)

    }


}

