package com.globalcorp.taskman


import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.globalcorp.taskman.databinding.FragmentMissionsBinding
import kotlinx.coroutines.launch
import com.globalcorp.taskman.utils.meow


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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activity = activity as AppCompatActivity
        activity.supportActionBar?.title = "Missions"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMissionsBinding.inflate(inflater, container, false)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the OverviewViewModel
        binding.xmlMainViewModel = viewModel

        networkObserveSetup(requireContext())

        layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )

        binding.missionsUiRcview.recyclerViewMissions.layoutManager = layoutManager

        missionAdapter = MissionAdapter()
        binding.missionsUiRcview.recyclerViewMissions.adapter = missionAdapter

        val dpSize = 8
        val pixels = (dpSize * resources.displayMetrics.density).toInt()
        binding.missionsUiRcview.recyclerViewMissions.addItemDecoration(SpacesItemDecoration(pixels))

        lifecycle.coroutineScope.launch {
            viewModel.allMissions.observe(viewLifecycleOwner) {
                missionAdapter!!.submitList(it)
            }
        }

        viewModel.missionsTabState.observe(viewLifecycleOwner) {
            updateButtonBar()
        }


        binding.missionsUiButtonsBar.missionsButtonsBarOption1Button.setOnClickListener {
            viewModel.setStateAvailable()
            meow(requireContext(), lifecycle.coroutineScope)
        }

        binding.missionsUiButtonsBar.missionsButtonsBarOption2Button.setOnClickListener {
            viewModel.setStateAccepted()
            meow(requireContext(), lifecycle.coroutineScope)
        }

        binding.missionsUiButtonsBar.missionsButtonsBarOption3Button.setOnClickListener {
            viewModel.setStateFinished()
            meow(requireContext(), lifecycle.coroutineScope)
        }

        viewModel.latestMission.observe(viewLifecycleOwner, Observer { mission ->
            //Toast.makeText(requireContext(), "Ilovepp", Toast.LENGTH_SHORT).show()

            if (mission != null) {
                sendNewMissionNotification(
                    "Terraco has sent you a new mission!",
                    "A new mission titled ${mission.title} is now available "
                )
                meow(requireContext(), lifecycle.coroutineScope)

            }
            /*else {
                sendNewMissionNotification(
                    "Terraco has revoked a mission",
                    "A mission was revoked from your available missions"
                )
            }*/


        })

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


    private fun sendNewMissionNotification(title: String, content: String) {
        val notificationChannelId = "mission_channel"
        val notificationManager =
            requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationBuilder =
            NotificationCompat.Builder(requireContext(), notificationChannelId)
                .setSmallIcon(R.drawable.terra_icon)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_HIGH)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                notificationChannelId,
                "Missions",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(0, notificationBuilder.build())
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //menuSetup()

    }


    private fun updateButtonBar() {
        when (viewModel.missionsTabState.value) {
            MissionsTabState.AVAILABLE -> {
                binding.missionsUiButtonsBar.missionsButtonsBarOption1Button
                    .setCompoundDrawablesWithIntrinsicBounds(
                        null, ContextCompat.getDrawable(
                            requireContext(), R.drawable.ic_free_mission_active
                        ), null, null
                    )
                binding.missionsUiButtonsBar.missionsButtonsBarOption1Button.setTextColor(
                    ContextCompat.getColor(requireContext(), R.color.white)
                )

                binding.missionsUiButtonsBar.missionsButtonsBarOption2Button
                    .setCompoundDrawablesWithIntrinsicBounds(
                        null, ContextCompat.getDrawable(
                            requireContext(), R.drawable.ic_accepted_mission_inactive
                        ), null, null
                    )
                binding.missionsUiButtonsBar.missionsButtonsBarOption2Button.setTextColor(
                    ContextCompat.getColor(requireContext(), R.color.light_grey)
                )

                binding.missionsUiButtonsBar.missionsButtonsBarOption3Button
                    .setCompoundDrawablesWithIntrinsicBounds(
                        null, ContextCompat.getDrawable(
                            requireContext(), R.drawable.ic_finished_mission_inactive
                        ), null, null
                    )
                binding.missionsUiButtonsBar.missionsButtonsBarOption3Button.setTextColor(
                    ContextCompat.getColor(requireContext(), R.color.light_grey)
                )
            }

            MissionsTabState.ACCEPTED -> {
                binding.missionsUiButtonsBar.missionsButtonsBarOption1Button
                    .setCompoundDrawablesWithIntrinsicBounds(
                        null, ContextCompat.getDrawable(
                            requireContext(), R.drawable.ic_free_mission_inactive
                        ), null, null
                    )
                binding.missionsUiButtonsBar.missionsButtonsBarOption1Button.setTextColor(
                    ContextCompat.getColor(requireContext(), R.color.light_grey)
                )

                binding.missionsUiButtonsBar.missionsButtonsBarOption2Button
                    .setCompoundDrawablesWithIntrinsicBounds(
                        null, ContextCompat.getDrawable(
                            requireContext(), R.drawable.ic_accepted_mission_active
                        ), null, null
                    )
                binding.missionsUiButtonsBar.missionsButtonsBarOption2Button.setTextColor(
                    ContextCompat.getColor(requireContext(), R.color.white)
                )

                binding.missionsUiButtonsBar.missionsButtonsBarOption3Button
                    .setCompoundDrawablesWithIntrinsicBounds(
                        null, ContextCompat.getDrawable(
                            requireContext(), R.drawable.ic_finished_mission_inactive
                        ), null, null
                    )
                binding.missionsUiButtonsBar.missionsButtonsBarOption3Button.setTextColor(
                    ContextCompat.getColor(requireContext(), R.color.light_grey)
                )
            }
            MissionsTabState.FINISHED -> {
                binding.missionsUiButtonsBar.missionsButtonsBarOption1Button
                    .setCompoundDrawablesWithIntrinsicBounds(
                        null, ContextCompat.getDrawable(
                            requireContext(), R.drawable.ic_free_mission_inactive
                        ), null, null
                    )
                binding.missionsUiButtonsBar.missionsButtonsBarOption1Button.setTextColor(
                    ContextCompat.getColor(requireContext(), R.color.light_grey)
                )

                binding.missionsUiButtonsBar.missionsButtonsBarOption2Button
                    .setCompoundDrawablesWithIntrinsicBounds(
                        null, ContextCompat.getDrawable(
                            requireContext(), R.drawable.ic_accepted_mission_inactive
                        ), null, null
                    )
                binding.missionsUiButtonsBar.missionsButtonsBarOption2Button.setTextColor(
                    ContextCompat.getColor(requireContext(), R.color.light_grey)
                )

                binding.missionsUiButtonsBar.missionsButtonsBarOption3Button
                    .setCompoundDrawablesWithIntrinsicBounds(
                        null, ContextCompat.getDrawable(
                            requireContext(), R.drawable.ic_finished_mission_active
                        ), null, null
                    )
                binding.missionsUiButtonsBar.missionsButtonsBarOption3Button.setTextColor(
                    ContextCompat.getColor(requireContext(), R.color.white)
                )

            }
            else -> {}
        }


    }

    /*private fun menuSetup() {
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

                    }
                    R.id.menu_dropdown_3 -> {

                    }

                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                findNavController().navigateUp()
                true
            }
            else -> true
        }
    }*/

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





