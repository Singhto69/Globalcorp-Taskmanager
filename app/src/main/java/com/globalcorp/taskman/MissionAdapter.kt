package com.globalcorp.taskman

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.globalcorp.taskman.database.MissionsSqlObject
import com.globalcorp.taskman.databinding.MissionsListItemBinding
import com.globalcorp.taskman.models.Mission

/**
 * This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present [List]
 * data, including computing diffs between lists.
 */
class MissionAdapter :
    ListAdapter<MissionsSqlObject, MissionAdapter.MissionsViewHolder>(DiffCallback) {

    /**
     * The MarsPhotosViewHolder constructor takes the binding variable from the associated
     * GridViewItem, which nicely gives it access to the full [MarsPhoto] information.
     */
    class MissionsViewHolder(private var binding: MissionsListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(mission: MissionsSqlObject) {
            binding.mission = mission
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()


        }
    }


    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of
     * [MarsPhoto] has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<MissionsSqlObject>() {
        override fun areItemsTheSame(
            oldItem: MissionsSqlObject,
            newItem: MissionsSqlObject
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: MissionsSqlObject,
            newItem: MissionsSqlObject
        ): Boolean {
            return oldItem.title == newItem.title
        }
    }

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MissionsViewHolder {
        return MissionsViewHolder(
            MissionsListItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: MissionsViewHolder, position: Int) {
        val mission = getItem(position)
        val thisContext = holder.itemView.context

        holder.bind(mission)

        holder.itemView.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("geo:0,0?q=${mission.location}")
            /*if (intent.resolveActivity(thisContext.packageManager) != null) {
                thisContext.startActivity(intent)
            } else {
                Toast.makeText(thisContext, "Google Maps is not installed", Toast.LENGTH_SHORT).show()
            }*/
            thisContext.startActivity(intent)
        }
    }
}


class MissionClickListener {

}