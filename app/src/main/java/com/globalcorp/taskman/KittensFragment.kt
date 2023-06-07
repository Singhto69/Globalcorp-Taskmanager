package com.globalcorp.taskman

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.globalcorp.taskman.databinding.FragmentKittensBinding
import com.globalcorp.taskman.databinding.FragmentMissionsBinding

class KittensFragment : Fragment() {
    private val viewModel: KittensViewModel by viewModels()

    private var _binding: FragmentKittensBinding? = null
    private val binding get() = _binding!!

    private var kittensAdapter : KittensAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentKittensBinding.inflate(inflater, container, false)
        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this
        binding.xmlViewModel = viewModel

        kittensAdapter = KittensAdapter()
        binding.kittensRecyclerview.adapter = kittensAdapter

        viewModel.images.observe(viewLifecycleOwner) {
            it?.let { kittensAdapter!!.submitList(it) }
        }

        binding.kittensRecyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                // Check if we have reached the bottom of the list
                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                    // Call your function here when scrolled to the bottom
                    viewModel.refresh()
                }
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


}

