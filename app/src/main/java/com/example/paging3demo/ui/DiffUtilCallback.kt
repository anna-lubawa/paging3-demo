package com.example.paging3demo.ui

import androidx.recyclerview.widget.DiffUtil
import com.example.paging3demo.domain.model.Tweet

class DiffUtilCallback : DiffUtil.ItemCallback<Tweet>() {
    override fun areItemsTheSame(oldItem: Tweet, newItem: Tweet): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Tweet, newItem: Tweet): Boolean {
        return oldItem == newItem
    }
}