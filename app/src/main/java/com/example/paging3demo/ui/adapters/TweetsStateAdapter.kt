package com.example.paging3demo.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.paging3demo.databinding.TweetListStateLayoutBinding

class TweetsStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<TweetsStateAdapter.TweetsStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): TweetsStateViewHolder {
        val binding = TweetListStateLayoutBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return TweetsStateViewHolder(binding, retry)
    }

    override fun onBindViewHolder(holder: TweetsStateViewHolder, loadState: LoadState) {
        holder.bindState(loadState)
    }

    class TweetsStateViewHolder(binding: TweetListStateLayoutBinding, retry: () -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        private val errorMessage: TextView = binding.errorMessage
        private val retryButton: Button = binding.retryButton
        private val progressBar: ProgressBar = binding.progressBar

        init {
            retryButton.setOnClickListener {
                retry()
            }
        }

        fun bindState(loadState: LoadState) {
            progressBar.isVisible = loadState is LoadState.Loading
            errorMessage.isVisible = loadState !is LoadState.Loading
            retryButton.isVisible = loadState !is LoadState.Loading
        }
    }
}