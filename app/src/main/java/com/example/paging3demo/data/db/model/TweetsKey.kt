package com.example.paging3demo.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tweetsKeys")
data class TweetsKey(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val next: String?,
    val previous: String?
)
