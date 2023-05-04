package com.example.m7019elab

/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.m7019elab.data.dummydatabase

/**
 * Adapter for the [RecyclerView] in [MainActivity].
 */
class DummyListAdapter :
    RecyclerView.Adapter<DummyListAdapter.DummyDataViewHolder>() {
    val datalist = dummydatabase()

    /**
     * Provides a reference for the views needed to display items in your list.
     */
    class DummyDataViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val DummyDataViewTextView = view.findViewById<TextView>(R.id.ilovebananas)
    }


    /**
     * Creates new views with R.layout.item_view as its template
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DummyDataViewHolder {
        val layout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.dummy_list_item, parent, false)

        return DummyDataViewHolder(layout)
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    /**
     * Replaces the content of an existing view with new data
     */
    override fun onBindViewHolder(holder: DummyDataViewHolder, position: Int) {


        //holder.DummyDataViewTextView.text
        /*
        --- Send via intent --
        val context = holder.itemView.context
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra(DetailActivity.LETTER, holder.button.text.toString())
        context.startActivity(intent)*/
        /*val action = LetterListFragmentDirections
            .actionLetterListFragmentToWordListFragment(letter = "b")
        holder.view.findNavController().navigate(action)
        // holder.button.text.toString()*/
    }
}


