package com.example.paging3demo.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.paging3demo.domain.model.Tweet

@Entity(tableName = "tweets")
data class Tweet(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val creationDate: String,
    val text: String
) {

    fun toDomainModel() : Tweet {
        return Tweet(
            id = id.toString(),
            creationDate = creationDate
                .replace("T", " ")
                .dropLast(5),
            text = text
        )
    }
}