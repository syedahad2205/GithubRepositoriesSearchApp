package com.syedcodes.githubreposearch.presentation.repo_info

sealed interface RepoInfoEvent {
    data class LoadContributors(val url: String) : RepoInfoEvent
}