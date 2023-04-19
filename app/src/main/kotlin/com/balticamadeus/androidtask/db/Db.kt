package com.balticamadeus.androidtask.db


import androidx.room.Database
import androidx.room.RoomDatabase
import com.balticamadeus.androidtask.api.model.Post
import com.balticamadeus.androidtask.api.model.User
import com.balticamadeus.androidtask.db.dao.PostDao
import com.balticamadeus.androidtask.db.dao.UserDao


@Database(
    entities = [
        Post::class,
        User::class,
    ],
    version = 2,
    exportSchema = false
)
abstract class DB : RoomDatabase() {
    abstract fun postDao(): PostDao
    abstract fun userDao(): UserDao




}