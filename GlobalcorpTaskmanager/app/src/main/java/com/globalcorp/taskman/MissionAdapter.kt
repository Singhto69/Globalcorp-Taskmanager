package com.globalcorp.taskman

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.globalcorp.taskman.database.missiondb
import com.globalcorp.taskman.model.mission

/* This is the adapter. The middleman between your dataset and recyclerview. It will take items
from your dataset , individually and format them to be used by the viewholders in the recyclerview.
* Viewholders are placeholder views in the recyclerview. , one viewholder represents one list item
* in recyclerview.
* A ViewHolder instance holds references to the individual views within a list item layout
* Context provides information on how to resolve string resources
* */

class MissionAdapter(
    private val dataset: List<mission>
) : RecyclerView.Adapter<MissionAdapter.itemViewHolder>() {

    class itemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        //val title: TextView = view.findViewById(R.id.mission_rcview_template_title)
        val location: TextView = view.findViewById(R.id.mission_rcview_template_location)
        val description: TextView = view.findViewById(R.id.mission_rcview_template_description)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): itemViewHolder {
        /*
        The layout inflater knows how to inflate an XML layout into a hierarchy of view objects.
        Take parents layout inflater then inflate this list item view.*/
        val adapterLayout =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.mission_rcview_template, parent, false)
        return itemViewHolder(adapterLayout)
    }

    override fun getItemCount() = dataset.size

    override fun onBindViewHolder(holder: itemViewHolder, position: Int) {
        /* this updates all the views referenced by the view holder , to reflect
        the current item ( to be displayed )
         */
        val currentitem = dataset[position]
        //holder.title.text = currentitem.title
        holder.location.text = currentitem!!.location
        holder.description.text = currentitem!!.description

    }



}