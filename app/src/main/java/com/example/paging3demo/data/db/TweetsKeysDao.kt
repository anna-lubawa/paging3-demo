package com.example.paging3demo.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.paging3demo.data.db.model.TweetsKey

@Dao
interface TweetsKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTweetsKeys(key: TweetsKey)

    @Query("SELECT * FROM tweetsKeys ORDER BY id DESC")
    suspend fun getTweetsKeys(): List<TweetsKey>

    @Query("DELETE FROM tweetsKeys")
    suspend fun clearAll()
}