package com.example.paging3demo.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.paging3demo.data.db.model.Tweet

@Dao
interface TweetsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTweets(tweets: List<Tweet>)

    @Query("SELECT * FROM tweets")
    fun getTweets(): PagingSource<Int, Tweet>

    @Query("DELETE FROM tweets")
    suspend fun clearAll()
}