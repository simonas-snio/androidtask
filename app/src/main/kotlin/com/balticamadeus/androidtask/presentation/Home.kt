package com.balticamadeus.androidtask.presentation

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshDefaults
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.balticamadeus.androidtask.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Home(viewModel: MainViewModel = hiltViewModel()) {

    val posts by viewModel.posts.collectAsState(initial = emptyList())
    val isRefreshing by remember { viewModel.isRefreshing }
    val error by remember { viewModel.error }

    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        refreshingOffset = PullRefreshDefaults.RefreshingOffset + 24.dp,
        onRefresh = {
            viewModel.getPosts(pull = true)
        }
    )

    if (error.isNotEmpty()) {
        ErrorDialog(
            error = error,
            retry = { viewModel.getPosts() },
            cancel = { viewModel.resetError() }
        )
    }

    Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
        if (posts.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ){}//When LazyColumn has no children, it does not fill max size, and is not scrollable hence pull refresh does not work, so a little work-around to counter that.
        } else {
            LazyColumn {
                items(posts) {
                    ListItem(it.postTitle, it.userName)
                    Divider(color = Color.Black)
                }
            }
        }

        PullRefreshIndicator(
            isRefreshing,
            pullRefreshState,
            Modifier.align(Alignment.TopCenter)
        )
    }
}


@Composable
fun ListItem(postTitle: String, userName: String) {
    Row(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Column(modifier = Modifier.padding(vertical = 8.dp)) {
            Text(userName, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            Text(postTitle)
        }
    }
}

@Composable
fun ErrorDialog(error: String, retry: () -> Unit, cancel: () -> Unit) {
    AlertDialog(
        onDismissRequest = { cancel() },
        title = { Text(text = error) },
        confirmButton = {
            Button(onClick = {
                retry();
            }) {
                Text(stringResource(id = R.string.button_retry))
            }
        },
        dismissButton = {
            Button(onClick = { cancel() }) {
                Text(stringResource(id = R.string.button_cancel))
            }
        }
    )
}
