package com.example.paging3demo.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.paging3demo.databinding.TweetLayoutBinding
import com.example.paging3demo.domain.model.Tweet
import com.example.paging3demo.ui.DiffUtilCallback

class TweetsAdapter : PagingDataAdapter<Tweet, TweetsAdapter.TweetsViewHolder>(DiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TweetsViewHolder {
        val binding = TweetLayoutBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return TweetsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TweetsViewHolder, position: Int) {
        getItem(position)?.let { tweet ->
            holder.bindTweet(tweet)
        }
    }

    class TweetsViewHolder(binding: TweetLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        private val date = binding.tweetDate
        private val content = binding.tweetText

        fun bindTweet(tweet: Tweet) {
            with(tweet) {
                date.text = creationDate
                content.text = text
            }
        }
    }
}