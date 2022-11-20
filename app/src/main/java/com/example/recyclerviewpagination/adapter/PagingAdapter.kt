package com.example.recyclerviewpagination.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewpagination.databinding.ItemDataBinding
import com.example.recyclerviewpagination.databinding.ItemLoadBinding
import com.example.recyclerviewpagination.model.Data
import com.squareup.picasso.Picasso

class PagingAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val userList: MutableList<Data> = mutableListOf()
    private var isLoadingAdded = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 0) {
            return LoadViewHolder(
                ItemLoadBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
        } else {
            return DataViewHolder(
                ItemDataBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewType = getItemViewType(position)

        if (viewType == 0)
            (holder as LoadViewHolder).bind()
        else
            (holder as DataViewHolder).bind(userList[position])
    }

    override fun getItemCount(): Int = userList.size

    override fun getItemViewType(position: Int): Int {
        return if (position == userList.size - 1 && isLoadingAdded) 0 else 1
    }

    inner class LoadViewHolder(private val binding: ItemLoadBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.progressbar.isVisible = true
        }
    }

    inner class DataViewHolder(private val binding: ItemDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Data) {
            binding.apply {
                Picasso.get()
                    .load(data.avatar)
                    .into(avatar)
                tv1.text = data.first_name
                tv2.text = data.last_name
            }
        }
    }

    fun addUserList(list: MutableList<Data>) {
        userList.addAll(list)
        notifyDataSetChanged()
    }

    fun offLoading() {
        isLoadingAdded = false
    }

    fun onLoading() {
        isLoadingAdded = true
    }

}