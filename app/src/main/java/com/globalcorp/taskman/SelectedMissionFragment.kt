package com.globalcorp.taskman

import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.Navigation
import com.globalcorp.taskman.databinding.FragmentSelectedMissionBinding
import com.globalcorp.taskman.models.Mission
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class SelectedMissionFragment : Fragment() {
    private var _binding: FragmentSelectedMissionBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSelectedMissionBinding.inflate(inflater)
        val mission = SelectedMissionFragmentArgs.fromBundle(requireArguments()).mission
        binding.mission = mission
        uiButtonsSetup()
        //val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        /*       val mission: Mission? = arguments?.let {
            val args = SelectedMissionFragmentArgs.fromBundle(it)
            args.mission
        }*/



        binding.selectedMissionAcceptButton.setOnClickListener {
            val db = Firebase.firestore
            val data = hashMapOf(
                "status" to 1
            )

            db.collection("missions").document(mission.id)
                .set(data, SetOptions.merge())
                .addOnSuccessListener {
                    Log.d(TAG, "DocumentSnapshot successfully written!")
                    Toast.makeText(context, "Request Sent!", Toast.LENGTH_SHORT).show()
                    /*val navController = requireActivity().findNavController(R.id.nav_host_fragment)
                    navController.navigate(R.id.action_selectedMissionFragment_to_missionsFragment)*/

                }
                .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
        }

        binding.selectedMissionGmapsButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("geo:0,0?q=${mission.location}")
            /*if (intent.resolveActivity(thisContext.packageManager) != null) {
                thisContext.startActivity(intent)
            } else {
                Toast.makeText(thisContext, "Google Maps is not installed", Toast.LENGTH_SHORT).show()
            }*/
            this.startActivity(intent)
        }

        binding.selectedMissionTimeReportButton.setOnClickListener {
            //val whydoyoucrash = R.id.action_selectedMissionFragment_to_missionsFragment
            //navController.navigate(R.id.action_selectedMissionFragment_to_missionsFragment)

            //val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
            val navController = Navigation.findNavController(requireView())
            //navController.navigate(R.id.action_selectedMissionFragment_to_missionsFragment)

            navController.popBackStack()
        }

        return binding.root
    }

    private fun uiButtonsSetup() {
        when (binding.mission!!.status) {
            0 -> {
                binding.selectedMissionAcceptButton.visibility = View.VISIBLE
                binding.selectedMissionGmapsButton.visibility = View.GONE
                binding.selectedMissionTimeReportButton.visibility = View.GONE
            }

            1 -> {
                binding.selectedMissionAcceptButton.visibility = View.GONE
                binding.selectedMissionGmapsButton.visibility = View.VISIBLE
                binding.selectedMissionTimeReportButton.visibility = View.VISIBLE
            }
            2 -> {
                binding.selectedMissionAcceptButton.visibility = View.GONE
                binding.selectedMissionGmapsButton.visibility = View.GONE
                binding.selectedMissionTimeReportButton.visibility = View.GONE

            }

        }
    }


    companion object {
        private const val ARG_MISSION = "mission"

        @JvmStatic
        fun newInstance(mission: Mission): SelectedMissionFragment {
            val fragment = SelectedMissionFragment()
            val args = Bundle()
            args.putParcelable(ARG_MISSION, mission)
            fragment.arguments = args
            return fragment
        }
    }


}

