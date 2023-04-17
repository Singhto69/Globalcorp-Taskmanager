package com.example.a003_affirmations.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a003_affirmations.R
import com.example.a003_affirmations.model.affirmation

/* This is the adapter. The middleman between your dataset and recyclerview. It will take items
from your dataset , individually and format them to be used by the viewholders in the recyclerview.
* Viewholders are placeholder views in the recyclerview. , one viewholder represents one list item
* in recyclerview.
* A ViewHolder instance holds references to the individual views within a list item layout
* Context provides information on how to resolve string resources
* */

class itemadapter(
    private val context: Context,
    private val dataset: List<affirmation>
) : RecyclerView.Adapter<itemadapter.itemViewHolder>() {

    class itemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.item_title)
        val imageView: ImageView = view.findViewById(R.id.item_image)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): itemViewHolder {
        /*
        The layout inflater knows how to inflate an XML layout into a hierarchy of view objects.
        Take parents layout inflater then inflate this list item view.*/
        val adapterLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)

        return itemViewHolder(adapterLayout)
    }

    override fun getItemCount() = dataset.size

    override fun onBindViewHolder(holder: itemViewHolder, position: Int) {
        /* this updates all the views referenced by the view holder , to reflect
        the current item ( to be displayed )
         */
        val currentitem = dataset[position]
        holder.textView.text = context.resources.getString(currentitem.stringResourceId)
        holder.imageView.setImageResource(currentitem.imageResourceId)

    }

}