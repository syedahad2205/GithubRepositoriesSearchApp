package com.syedcodes.githubreposearch.presentation.repo_listings

import com.syedcodes.githubreposearch.domain.model.RepoListing

sealed interface RepoListingEvent {
    data class OnSearchQueryChanged(val query: String) : RepoListingEvent
    data class CacheData(val data: List<RepoListing>) : RepoListingEvent
    data object ShowCachedData : RepoListingEvent
}