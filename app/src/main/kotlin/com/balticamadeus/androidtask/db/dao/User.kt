package com.balticamadeus.androidtask.db.dao

import androidx.room.Dao
import androidx.room.Upsert
import com.balticamadeus.androidtask.api.model.User


@Dao
interface UserDao {
    @Upsert
    suspend fun upsertUser(user: User)
}