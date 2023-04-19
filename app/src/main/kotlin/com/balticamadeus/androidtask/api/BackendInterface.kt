package com.balticamadeus.androidtask.api

import com.balticamadeus.androidtask.api.model.Post
import com.balticamadeus.androidtask.api.model.User
import retrofit2.http.GET
import retrofit2.http.Path

interface BackendInterface {

    @GET("posts")
    suspend fun getPosts(): List<Post>

    @GET("users/{id}")
    suspend fun getUser(
        @Path("id") id: Int
    ): User
}