package com.balticamadeus.androidtask.api

import com.balticamadeus.androidtask.BuildConfig
import com.balticamadeus.androidtask.api.model.Post
import com.balticamadeus.androidtask.api.model.User
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject

class Backend @Inject constructor(
    private val api: BackendInterface
) {

    suspend fun getPosts(): Resource<List<Post>> {
        val response = try {
            api.getPosts()
        } catch (e: Exception) {
            e.printStackTrace()
            return Resource.Error("An unknown error")
        }
        return Resource.Success(response)
    }

    suspend fun getUser(id: Int): Resource<User> {
        val response = try {
            api.getUser(id)
        } catch (e: Exception) {
            e.printStackTrace()
            return Resource.Error("An unknown error")
        }
        return Resource.Success(response)
    }


    companion object {
        fun createBackend(): BackendInterface {
            val client = OkHttpClient.Builder()
            client.addInterceptor {
                val request = it.request()
                val builder = request.newBuilder()
                val url = request.url.newBuilder()
                it.proceed(builder.url(url.build()).build())
            }
            return getRetrofit(client)
        }

        private fun getRetrofit(client: OkHttpClient.Builder): BackendInterface {
            return Retrofit.Builder()
                .client(client.addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }).build())
                .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().build()))
                .baseUrl(BuildConfig.API_URL)
                .build()
                .create(BackendInterface::class.java)
        }
    }
}