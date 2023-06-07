package com.globalcorp.taskman



import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.globalcorp.taskman.databinding.FragmentMissionsBinding
import kotlinx.coroutines.launch



class MissionsFragment : Fragment() {
    private var _binding: FragmentMissionsBinding? = null
    private val binding get() = _binding!!

    private var missionAdapter: MissionAdapter? = null

    private val viewModel: MissionsViewModel by activityViewModels {
        MissionsViewModelFactory(
            (activity?.application as MissionsApplication).database
                .missionsDao()

        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMissionsBinding.inflate(inflater, container, false)
        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this
        // Giving the binding access to the OverviewViewModel
        binding.xmlViewModel = viewModel


        missionAdapter = MissionAdapter()
        binding.recyclerViewMissions.adapter = missionAdapter

        /*viewModel.allMissions.observe(viewLifecycleOwner) {
            it?.let { missionAdapter!!.submitList(it) }
        }*/

        lifecycle.coroutineScope.launch {
            viewModel.allMissions().collect() {
                missionAdapter!!.submitList(it)
            }
        }

        binding.recyclerViewMissions.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
        setupMenu()
    }

    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {


            override fun onPrepareMenu(menu: Menu) {
                // Handle for example visibility of menu items
                val menuItem1 = menu.findItem(R.id.menu_dropdown_1)
                menuItem1.title = "Refresh"
                val menuItem2 = menu.findItem(R.id.menu_dropdown_2)
                menuItem2.title = "Wipe"

            }

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.main_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Validate and handle the selected menu item
                when (menuItem.itemId) {
                    R.id.menu_dropdown_1 -> {
                        viewModel.refresh()
                    }
                    R.id.menu_dropdown_2 -> {

                        viewModel.deleteAll()

                    }
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}

/*val mainActivity = activity as MainActivity
mainActivity.supportActionBar?.apply{
    title = "My Fragment"
    setBackgroundDrawable(ColorDrawable(Color.RED))*/


// Customize other properties like background color, menu items, etc.





