package com.globalcorp.taskman

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.globalcorp.taskman.databinding.MissionsListItemBinding

class MissionAdapter :
    ListAdapter<com.globalcorp.taskman.models.Mission, MissionAdapter.MissionsViewHolder>(
        MissionAdapter
    ) {

    class MissionsViewHolder(private var binding: MissionsListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(mission: com.globalcorp.taskman.models.Mission) {
            binding.mission = mission
            /* This is important, because it forces the data binding to execute immediately,
             which allows the RecyclerView to make the correct view size measurements */
            binding.executePendingBindings()
        }
    }


    companion object DiffCallback : DiffUtil.ItemCallback<com.globalcorp.taskman.models.Mission>() {
        override fun areItemsTheSame(
            oldItem: com.globalcorp.taskman.models.Mission,
            newItem: com.globalcorp.taskman.models.Mission
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: com.globalcorp.taskman.models.Mission,
            newItem: com.globalcorp.taskman.models.Mission
        ): Boolean {
            return oldItem.title == newItem.title
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): MissionsViewHolder {
        return MissionsViewHolder(
            MissionsListItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }


    override fun onBindViewHolder(holder: MissionsViewHolder, position: Int) {
        val mission = getItem(position)
        val thisContext = holder.itemView.context
        holder.bind(mission)
        /*holder.itemView.setOnClickListener {
            val fragment = SelectedMissionFragment.newInstance(mission)

            val fragmentManager =
                (holder.itemView.context as AppCompatActivity).supportFragmentManager
            fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment)
                .addToBackStack(null).commit()
        }*/

        holder.itemView.setOnClickListener {
            val action = MissionsFragmentDirections.actionMissionsFragmentToSelectedMissionFragment(mission)
            val navController = Navigation.findNavController(holder.itemView)
            navController.navigate(action)
        }

    }
}


/*holder.itemView.setOnClickListener {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse("geo:0,0?q=${mission.location}")
    /*if (intent.resolveActivity(thisContext.packageManager) != null) {
        thisContext.startActivity(intent)
    } else {
        Toast.makeText(thisContext, "Google Maps is not installed", Toast.LENGTH_SHORT).show()
    }*/
    thisContext.startActivity(intent)
}*/
