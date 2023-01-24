package com.example.paging3demo.data.network.model

import com.google.gson.annotations.SerializedName

data class TwitterMeta(
    @SerializedName("result_count")
    val resultCount: Int,
    @SerializedName("next_token")
    val nextToken: String?,
    @SerializedName("previous_token")
    val previousToken: String?
)
