package com.globalcorp.taskman

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