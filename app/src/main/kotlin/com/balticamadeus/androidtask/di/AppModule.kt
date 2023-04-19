package com.balticamadeus.androidtask.di

import com.balticamadeus.androidtask.api.Backend
import com.balticamadeus.androidtask.api.BackendInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideBackend(
        api: BackendInterface
    ) = Backend(api)

    @Singleton
    @Provides
    fun provideBackendInterface() = Backend.createBackend()

}