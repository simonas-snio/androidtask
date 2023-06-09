package com.balticamadeus.androidtask.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.balticamadeus.androidtask.api.Backend
import com.balticamadeus.androidtask.api.Resource
import com.balticamadeus.androidtask.db.DB
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val api: Backend,
    private val db: DB
) : ViewModel() {

    val posts = db.postDao().getPostsWithUser()
    val error = mutableStateOf("")
    val isRefreshing = mutableStateOf(false)
    val loading = mutableStateOf(false)

    init {
        getPosts()
    }

    fun getPosts(pullRefresh: Boolean = false) {
        resetError()
        if (pullRefresh) {
            isRefreshing.value = true
        } else {
            loading.value = true
        }
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = api.getPosts()) {
                is Resource.Success -> {
                    result.data?.let {
                        it.forEach { post ->
                            db.postDao().upsertPost(post)
                        }
                        it.distinctBy { post ->
                            post.userId
                        }.onEach { distinctUser ->
                            getUser(distinctUser.userId)
                        }
                    }
                    withContext(Dispatchers.Main) {
                        error.value = ""
                        isRefreshing.value = false
                        loading.value = false

                    }
                }
                is Resource.Error -> {
                    withContext(Dispatchers.Main) {
                        error.value = result.message ?: "Error"
                        isRefreshing.value = false
                        loading.value = false
                    }
                }
            }
        }
    }

    private fun getUser(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = api.getUser(id)) {
                is Resource.Success -> {
                    result.data?.let {
                        db.userDao().upsertUser(it)
                    }
                }
                is Resource.Error -> {
                    withContext(Dispatchers.Main) {
                        error.value = result.message ?: "Error"
                    }
                }
            }
        }
    }

    fun resetError() {
        error.value = ""
    }
}