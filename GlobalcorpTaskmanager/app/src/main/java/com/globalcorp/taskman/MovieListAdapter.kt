package com.ltu.m7019e.v23.themoviedb.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.globalcorp.taskman.databinding.MissionRcviewTemplateBinding
import com.globalcorp.taskman.models.Mission

//private val movieClickListener: MovieListClickListener <- into movielist adapter
// , movieClickListener: MovieListClickListener <- into bind


class MovieListAdapter() :  ListAdapter<Mission, MovieListAdapter.ViewHolder>(MovieListDiffCallback()){
    class ViewHolder(private var binding: MissionRcviewTemplateBinding) : RecyclerView.ViewHolder(binding.root) {
        // MovieListItemBinding
        fun bind(mission: Mission) {
            //binding.mission = mission
            //binding.clickListener = movieClickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup) : ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MissionRcviewTemplateBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class MovieListDiffCallback : DiffUtil.ItemCallback<Mission>() {
    override fun areItemsTheSame(oldItem: Mission, newItem: Mission): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Mission, newItem: Mission): Boolean {
        return oldItem == newItem
    }

}

/*class MovieListClickListener(val clickListener: (movie: Movie) -> Unit) {
    fun onClick(movie: Movie) = clickListener(movie)
}*/