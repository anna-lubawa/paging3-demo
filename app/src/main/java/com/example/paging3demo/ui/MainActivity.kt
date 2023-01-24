package com.example.paging3demo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.map
import com.example.paging3demo.databinding.ActivityMainBinding
import com.example.paging3demo.ui.adapters.TweetsAdapter
import com.example.paging3demo.ui.adapters.TweetsStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@ExperimentalPagingApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private val tweetsAdapter = TweetsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.tweetsRecyclerView.adapter = tweetsAdapter.withLoadStateHeaderAndFooter(
            header = TweetsStateAdapter { tweetsAdapter.retry() },
            footer = TweetsStateAdapter { tweetsAdapter.retry() }
        )

//        tweetsAdapter.addLoadStateListener {
//            val refreshState = it.mediator?.refresh
//            binding.tweetsRecyclerView.isVisible = refreshState is LoadState.NotLoading
//        }

        lifecycleScope.launch {
            viewModel.getTweets().collectLatest { pagingData ->
                tweetsAdapter.submitData(pagingData)
            }
        }
    }
}