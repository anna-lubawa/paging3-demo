package com.example.paging3demo.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.paging3demo.data.repo.TweetsRepository
import com.example.paging3demo.domain.model.Tweet
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class MainViewModel @Inject constructor(
    private val tweetsRepository: TweetsRepository
) : ViewModel() {

    fun getTweets(): Flow<PagingData<Tweet>> {
        return tweetsRepository.fetchTweets().cachedIn(viewModelScope)
    }
}