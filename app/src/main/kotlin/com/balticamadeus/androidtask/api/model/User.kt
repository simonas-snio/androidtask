package com.balticamadeus.androidtask.api.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = "users")
data class User(
    @Json(name = "id")
    @PrimaryKey
    val id: Int,
    @Json(name = "name")
    val name: String
)