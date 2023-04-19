package com.balticamadeus.androidtask.di

import android.content.Context
import androidx.room.Room
import com.balticamadeus.androidtask.api.Backend
import com.balticamadeus.androidtask.api.BackendInterface
import com.balticamadeus.androidtask.db.DB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) =
        Room.databaseBuilder(
            appContext,
            DB::class.java, "AndroidTask_DB"
        ).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideBackend(
        api: BackendInterface
    ) = Backend(api)

    @Singleton
    @Provides
    fun provideBackendInterface() = Backend.createBackend()

}