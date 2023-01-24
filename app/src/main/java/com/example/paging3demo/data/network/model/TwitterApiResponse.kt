package com.example.paging3demo.data.network.model

data class TwitterApiResponse(
    val data: List<Tweet>,
    val meta: TwitterMeta
)
