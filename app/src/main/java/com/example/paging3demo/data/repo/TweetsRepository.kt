package com.example.paging3demo.data.repo

import androidx.paging.*
import com.example.paging3demo.data.db.TwitterDatabase
import com.example.paging3demo.data.network.TwitterService
import com.example.paging3demo.domain.model.Tweet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TweetsRepository @Inject constructor(
    private val twitterService: TwitterService,
    private val twitterDatabase: TwitterDatabase
) {

//    Solution 1 - with PagingSource
//    ----------------------------------
//    fun fetchTweets(): Flow<PagingData<Tweet>> {
//        return Pager(
//            PagingConfig(initialLoadSize = 20, pageSize = 20)
//        ) {
//            TweetsPagingSource(twitterService)
//        }.flow.map { pagingData ->
//            pagingData.map { it.toDomainModel() }
//        }
//    }

//    Solution 2 - with RemoteMediator
//    ----------------------------------
    @ExperimentalPagingApi
    fun fetchTweets(): Flow<PagingData<Tweet>> {
        return Pager(
            config = PagingConfig(initialLoadSize = 20, pageSize = 20),
            remoteMediator = TweetsRemoteMediator(twitterService, twitterDatabase),
            pagingSourceFactory = { twitterDatabase.tweetsDao().getTweets() }
        ).flow.map { pagingData ->
            pagingData.map { it.toDomainModel() }
        }
    }
}