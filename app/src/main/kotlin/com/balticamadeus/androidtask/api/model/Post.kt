package com.balticamadeus.androidtask.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Post(
    @Json(name = "title")
    val title: String,
    @Json(name = "userId")
    val userId: Int
)