package com.chslcompany.spaceflightnews.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chslcompany.spaceflightnews.core.setOnSingleClickListener
import com.chslcompany.spaceflightnews.data.model.Post
import com.chslcompany.spaceflightnews.databinding.ItemPostAdapterBinding

class PostListAdapter(
    private val itemClick:((item:Post) -> Unit)
) : ListAdapter<Post, PostListAdapter.PostViewHolder>(PostListAdapter) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder =
        PostViewHolder.from(parent)

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) =
        holder.bind(getItem(position),itemClick)

    class PostViewHolder private constructor(private val binding: ItemPostAdapterBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): PostViewHolder {
                val binding: ItemPostAdapterBinding = ItemPostAdapterBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return PostViewHolder(binding)
            }
        }

        fun bind(item: Post, itemClick : ((item : Post) -> Unit)) {
            binding.post = item
            itemView.setOnSingleClickListener {
               itemClick.invoke(item)
            }
        }
    }

    private companion object : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean =
           oldItem == newItem
    }

}