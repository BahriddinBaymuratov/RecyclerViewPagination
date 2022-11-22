package com.example.recyclerviewpagination.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recyclerviewpagination.R
import com.example.recyclerviewpagination.databinding.ItemDataBinding
import com.example.recyclerviewpagination.model.CharacterData

class Paging3Adapter :
    PagingDataAdapter<CharacterData, Paging3Adapter.Paging3ViewHolder>(DiffCallBack()) {
    private class DiffCallBack : DiffUtil.ItemCallback<CharacterData>() {
        override fun areItemsTheSame(oldItem: CharacterData, newItem: CharacterData): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: CharacterData, newItem: CharacterData): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: Paging3ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Paging3ViewHolder {
        return Paging3ViewHolder(
            ItemDataBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class Paging3ViewHolder(private val binding: ItemDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: CharacterData?) {
            with(binding) {
                if (data != null) {
                    Glide.with(avatar)
                        .load(data.image)
                        .circleCrop()
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .into(avatar)
                    tv1.text = data.name
                    tv2.text = data.species
                }
            }
        }
    }
}