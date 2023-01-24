package com.example.paging3demo.data.repo

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.paging3demo.data.network.model.Tweet
import com.example.paging3demo.data.network.TwitterService
import retrofit2.HttpException
import java.io.IOException

class TweetsPagingSource(
    private val twitterService: TwitterService
) : PagingSource<String, Tweet>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Tweet> {
        return try {
            val response = twitterService.fetchTweets(
                loadSize = params.loadSize,
                paginationToken = params.key
            )
            val tweets = response.data

            LoadResult.Page(
                tweets,
                response.meta.previousToken,
                response.meta.nextToken
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<String, Tweet>): String? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPageIndex = state.pages.indexOf(state.closestPageToPosition(anchorPosition))
            state.pages.getOrNull(anchorPageIndex + 1)?.prevKey
                ?: state.pages.getOrNull(anchorPageIndex - 1)?.nextKey
        }
    }
}