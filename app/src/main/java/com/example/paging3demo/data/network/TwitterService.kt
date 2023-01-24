package com.example.paging3demo.data.network

import com.example.paging3demo.BuildConfig
import com.example.paging3demo.data.network.model.TwitterApiResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


interface TwitterService {

    @GET("users/2244994945/tweets")
    suspend fun fetchTweets(
        @Header("Authorization") token: String = "Bearer ${BuildConfig.TWITTER_BEARER_TOKEN}",
        @Query("tweet.fields") tweetFields: String = "created_at",
        @Query("max_results") loadSize: Int = 0,
        @Query("pagination_token") paginationToken: String? = null
    ): TwitterApiResponse
}


