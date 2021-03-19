package com.rado.friends.ui.friends

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rado.friends.data.Friend
import com.rado.friends.databinding.ItemFriendBinding

class FriendAdapter(private val listener: OnItemClickListener) :
    ListAdapter<Friend, FriendAdapter.FriendViewHolder>(DiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val binding = ItemFriendBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FriendViewHolder(binding)


    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)


    }

    inner class FriendViewHolder(private val binding: ItemFriendBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                root.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val friend = getItem(position)
                        listener.onItemClick(friend)
                    }
                }
            }

        }

        fun bind(friend: Friend) {
            binding.apply {
                tvFriendName.text = friend.name
            }
        }

    }

    interface OnItemClickListener {
        fun onItemClick(friend: Friend)
    }

    class DiffCallback : DiffUtil.ItemCallback<Friend>() {
        override fun areItemsTheSame(oldItem: Friend, newItem: Friend): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Friend, newItem: Friend): Boolean =
            oldItem == newItem

    }

}