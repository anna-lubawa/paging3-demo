package com.example.paging3demo.data.network.model

import com.example.paging3demo.domain.model.Tweet
import com.google.gson.annotations.SerializedName

data class Tweet(
    val id: String,
    @SerializedName("created_at")
    val creationDate: String,
    val text: String
) {

    fun toDomainModel() : Tweet {
        return Tweet(
            id = id,
            creationDate = creationDate
                .replace("T", " ")
                .dropLast(5),
            text = text
        )
    }

    fun toDatabaseModel() : com.example.paging3demo.data.db.model.Tweet {
        return com.example.paging3demo.data.db.model.Tweet(
            id = 0,
            creationDate = creationDate,
            text = text
        )
    }
}