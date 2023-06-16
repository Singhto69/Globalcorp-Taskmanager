package com.globalcorp.taskman


import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
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

    private val viewModel: MissionsViewModel by activityViewModels {
        MissionsViewModelFactory(
            (activity?.application as MissionsApplication).database.missionsDao()

        )
    }
    private var connectivityManager: ConnectivityManager? = null
    private var networkCallback: ConnectivityManager.NetworkCallback? = null

    private var missionAdapter: MissionAdapter? = null
    private var layoutManager: LinearLayoutManager? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMissionsBinding.inflate(inflater, container, false)
        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this
        // Giving the binding access to the OverviewViewModel
        binding.xmlViewModel = viewModel

        networkObserveSetup(requireContext())

        layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )
        binding.recyclerViewMissions.layoutManager = layoutManager

        missionAdapter = MissionAdapter()
        binding.recyclerViewMissions.adapter = missionAdapter

        lifecycle.coroutineScope.launch {
            viewModel.allMissions().collect() {
                missionAdapter!!.submitList(it)
            }
        }

        /*binding.recyclerViewMissions.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
        })*/


        return binding.root


    }

    private fun networkObserveSetup(context: Context) {
        connectivityManager =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // NetworkCallBack receive network status changes
        networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                println("Connection")
                viewModel.refresh()
            }

            override fun onLost(network: Network) {
                println("Lost connection")
                viewModel.refresh()
            }

        }

        /*
        build a NetworkRequest with the NET_CAPABILITY_INTERNET capability,
        indicating that we want to be notified for networks that have internet access.
        Then, we call registerNetworkCallback() on the ConnectivityManager,
        passing the networkRequest and networkCallback as parameters.
         */
        /*val networkRequest =
            NetworkRequest.Builder().addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build()*/

        // Register the NetworkCallback with the ConnectivityManager:
        /*connectivityManager!!.registerNetworkCallback(
            networkRequest,
            networkCallback as ConnectivityManager.NetworkCallback
        )*/

        //connectivityManager!!.registerNetworkCallback(networkRequest, networkCallback)

        // Cleanup the connectivity manager
        //connectivityManager.unregisterNetworkCallback(networkCallback)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        menuSetup()
    }

    private fun menuSetup() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {


            override fun onPrepareMenu(menu: Menu) {
                // Handle for example visibility of menu items
                val menuItem1 = menu.findItem(R.id.menu_dropdown_1)
                menuItem1.title = "Refresh"
                val menuItem2 = menu.findItem(R.id.menu_dropdown_2)
                menuItem2.title = "Wipe"
                val menuItem3 = menu.findItem(R.id.menu_dropdown_3)
                menuItem3.title = "Horizontal"
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
                    R.id.menu_dropdown_3 -> {
                        layoutManager = LinearLayoutManager(
                            requireContext(), LinearLayoutManager.HORIZONTAL, false
                        )
                        binding.recyclerViewMissions.layoutManager = layoutManager
                    }

                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onStart() {
        super.onStart()
        registerNetworkCallback()
    }

    override fun onStop() {
        super.onStop()
        unregisterNetworkCallback()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterNetworkCallback()
        networkCallback = null
        connectivityManager = null

    }

    private fun registerNetworkCallback() {
        networkCallback?.let { connectivityManager?.registerDefaultNetworkCallback(it) }
    }

    private fun unregisterNetworkCallback() {
        networkCallback?.let { connectivityManager?.unregisterNetworkCallback(it) }
    }


}

/*val mainActivity = activity as MainActivity
mainActivity.supportActionBar?.apply{
    title = "My Fragment"
    setBackgroundDrawable(ColorDrawable(Color.RED))*/


// Customize other properties like background color, menu items, etc.





