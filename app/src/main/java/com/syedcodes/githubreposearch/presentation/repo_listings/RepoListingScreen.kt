package com.syedcodes.githubreposearch.presentation.repo_listings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.syedcodes.githubreposearch.data.mapper.toRepoListing
import com.syedcodes.githubreposearch.domain.model.RepoListing
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepoListingScreen(
    viewModel: RepoListingViewModel = hiltViewModel(),
    onRepoClick: (RepoListing) -> Unit
) {
    val state = viewModel.state
    val response = viewModel.repoResponse.collectAsLazyPagingItems()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = state.searchQuery,
            onValueChange = {
                viewModel.onEvent(RepoListingEvent.OnSearchQueryChanged(it))
            },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            placeholder = {
                Text(text = "Type here", color = Color.Gray)
            },
            maxLines = 1,
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color(0xFFFFA726),
                unfocusedIndicatorColor = Color.Gray,
                cursorColor = Color(0xFFFFA726)
            )
        )


        Spacer(modifier = Modifier.height(16.dp))

        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(response.itemCount) { i ->
                    val repo = response[i]
                    repo?.toRepoListing()?.let {
                        RepoItem(
                            repo = it,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onRepoClick(repo.toRepoListing())
                                }
                                .padding(8.dp)
                                .background(Color.White, RoundedCornerShape(8.dp))
                                .shadow(1.dp, RoundedCornerShape(8.dp))
                                .padding(16.dp)
                        )
                    }
                    if (i < response.itemCount) {
                        Divider(
                            color = Color.Gray,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }
                }

                item {
                    when (val loadState = response.loadState.append) {
                        is LoadState.Error -> {
                            ErrorItem(
                                errorMessage = loadState.error.localizedMessage,
                                onRetryClick = { response.retry() }
                            )
                        }

                        is LoadState.Loading -> {
                            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                                CircularProgressIndicator()
                            }
                        }

                        is LoadState.NotLoading -> Unit
                    }
                }

                item {
                    when (val loadState = response.loadState.refresh) {
                        is LoadState.Error -> {
                            ErrorItem(
                                errorMessage = loadState.error.localizedMessage,
                                onRetryClick = { response.retry() }
                            )
                        }

                        is LoadState.Loading -> {
                            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                                CircularProgressIndicator()
                            }
                        }

                        is LoadState.NotLoading -> Unit
                    }
                }
            }
        }
    }
}

@Composable
fun ErrorItem(errorMessage: String?, onRetryClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.White, RoundedCornerShape(8.dp))
            .shadow(1.dp, RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text(text = errorMessage ?: "Unknown Error", color = Color.Red, style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = onRetryClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA726)),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Retry", color = Color.White)
        }
    }
}

@Composable
fun RepoItem(repo: RepoListing, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = repo.githubRepoName,
            style = MaterialTheme.typography.titleMedium.copy(color = Color(0xFF333333)),
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Owner: ${repo.repoOwnerName}",
            style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF666666))
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = repo.description ?: "No description available",
            style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF444444))
        )
    }
}
