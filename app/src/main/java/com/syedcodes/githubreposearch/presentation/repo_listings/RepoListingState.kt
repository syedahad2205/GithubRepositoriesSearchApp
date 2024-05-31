package com.syedcodes.githubreposearch.presentation.repo_listings

import com.syedcodes.githubreposearch.domain.model.RepoListing

data class RepoListingState(
    val repos: List<RepoListing> = emptyList(),
    val isLoading: Boolean = false,
    val searchQuery: String = ""
)
