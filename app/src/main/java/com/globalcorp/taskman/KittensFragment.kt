package com.globalcorp.taskman

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.globalcorp.taskman.databinding.FragmentKittensBinding

class KittensFragment : Fragment() {
    private val viewModel: KittensViewModel by viewModels()

    private var _binding: FragmentKittensBinding? = null
    private val binding get() = _binding!!

    private var kittensAdapter: KittensAdapter? = null

    private var initialLoadCompleted = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentKittensBinding.inflate(inflater, container, false)
        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this
        binding.xmlViewModel = viewModel

        kittensAdapter = KittensAdapter(viewModel)
        binding.kittensRecyclerview.adapter = kittensAdapter

        val activity = activity as AppCompatActivity
        activity.supportActionBar?.title = "Cats"

        viewModel.images.observe(viewLifecycleOwner) {
            it?.let { kittensAdapter!!.submitList(it) }
            initialLoadCompleted = true
        }

        binding.kittensRecyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy <= 0) return

                if (!initialLoadCompleted || viewModel.isRefreshing.value == true) return

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                // Check if we have reached the bottom of the list
                if (visibleItemCount + firstVisibleItemPosition + 5 >= totalItemCount && firstVisibleItemPosition >= 0) {
                    if (viewModel.isRefreshing.value == true) return
                    viewModel.meow(requireContext())
                    viewModel.refresh()
                    layoutManager.scrollToPosition(0)

                }
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


}

