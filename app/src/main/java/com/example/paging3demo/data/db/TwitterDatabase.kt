package com.example.paging3demo.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.paging3demo.data.db.model.Tweet
import com.example.paging3demo.data.db.model.TweetsKey

@Database(entities = [Tweet::class, TweetsKey::class], version = 1)
abstract class TwitterDatabase : RoomDatabase() {

    abstract fun tweetsDao(): TweetsDao
    abstract fun tweetsKeysDao(): TweetsKeysDao
}