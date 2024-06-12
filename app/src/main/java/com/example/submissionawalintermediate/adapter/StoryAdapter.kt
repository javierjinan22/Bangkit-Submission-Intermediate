package com.example.submissionawalintermediate.adapter

import android.content.Intent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.submissionawalintermediate.R
import com.example.submissionawalintermediate.databinding.ItemStoryBinding
import com.example.submissionawalintermediate.response.ListStoryItem
import com.example.submissionawalintermediate.view.detail.DetailActivity
import com.example.submissionawalintermediate.view.detail.DetailActivity.Companion.EXTRA_DATA

class StoryAdapter : PagingDataAdapter<ListStoryItem, StoryAdapter.ListViewHolder>(DIFF_CALLBACK)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    inner class ListViewHolder(private var binding: ItemStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ListStoryItem) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(item.photoUrl)
                    .error(R.drawable.baseline_broken_image_24)
                    .into(ivItemPhoto)
                tvItemName.text = item.name
                tvItemDescription.text = item.description

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(EXTRA_DATA, item)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    companion object {
         val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}