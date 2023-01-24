package com.example.paging3demo.data.repo

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.paging3demo.data.db.model.Tweet
import com.example.paging3demo.data.db.TwitterDatabase
import com.example.paging3demo.data.db.model.TweetsKey
import com.example.paging3demo.data.network.TwitterService
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class TweetsRemoteMediator(
    private val twitterService: TwitterService,
    private val twitterDatabase: TwitterDatabase
) : RemoteMediator<Int, Tweet>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Tweet>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKeys = if (state.pages.lastOrNull { it.data.isNotEmpty() } != null) {
                        getTweetsKeys()
                    } else null

                    if (remoteKeys?.next == null) {
                        return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    }

                    remoteKeys.next
                }
            }

            val response = twitterService.fetchTweets(
                loadSize = state.config.pageSize,
                paginationToken = loadKey
            )

            val tweets = response.data.map { it.toDatabaseModel() }

            twitterDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    twitterDatabase.tweetsDao().clearAll()
                    twitterDatabase.tweetsKeysDao().clearAll()
                }

                twitterDatabase.tweetsKeysDao()
                    .saveTweetsKeys(
                        TweetsKey(0, response.meta.nextToken, response.meta.previousToken)
                    )
                twitterDatabase.tweetsDao().saveTweets(tweets)
            }

            MediatorResult.Success(endOfPaginationReached = response.meta.nextToken == null)
        } catch (exception: IOException) {
            MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            MediatorResult.Error(exception)
        }
    }

    private suspend fun getTweetsKeys(): TweetsKey? {
        return twitterDatabase.tweetsKeysDao().getTweetsKeys().firstOrNull()
    }
}