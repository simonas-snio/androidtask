package com.balticamadeus.androidtask.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.balticamadeus.androidtask.api.model.Post
import com.balticamadeus.androidtask.api.model.PostTitleWithUsername
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {

    @Upsert
    suspend fun upsertPost(post: Post)

    @Query("SELECT posts.title as postTitle, users.name AS userName FROM posts,users WHERE posts.userId = users.id")
    fun getPostsWithUser(): Flow<List<PostTitleWithUsername>>
}