package com.balticamadeus.androidtask.api.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = "posts")
data class Post(
    @Json(name = "title")
    val title: String,
    @Json(name = "userId")
    @PrimaryKey
    val userId: Int
)