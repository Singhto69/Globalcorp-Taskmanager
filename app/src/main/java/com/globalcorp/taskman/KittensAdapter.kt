package com.globalcorp.taskman

/*
 * Copyright (C) 2021 The Android Open Source Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.globalcorp.taskman.databinding.CatGridItemBinding
import com.globalcorp.taskman.models.CatObject


class KittensAdapter(private val viewModel: KittensViewModel) :
    ListAdapter<CatObject, KittensAdapter.KittensViewHolder>(DiffCallback) {


    class KittensViewHolder(private var binding: CatGridItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun doSomethingWithViewModel(context: Context) {
            // Access the ViewModel and call the desired function
            val viewModel = ViewModelProvider(itemView.context as ViewModelStoreOwner)
                .get(KittensViewModel::class.java)
            viewModel.meow(context)
        }


        fun bind(catObject: CatObject) {
            binding.cat = catObject
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<CatObject>() {
        override fun areItemsTheSame(oldItem: CatObject, newItem: CatObject): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CatObject, newItem: CatObject): Boolean {
            return oldItem.imgUrl == newItem.imgUrl
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): KittensViewHolder {


        return KittensViewHolder(
            CatGridItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: KittensViewHolder, position: Int) {
        val cat = getItem(position)
        val thisContext = holder.itemView.context
        holder.bind(cat)
        holder.itemView.setOnClickListener {
            holder.doSomethingWithViewModel(thisContext)
        }
    }


}