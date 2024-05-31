package com.syedcodes.githubreposearch.domain.model

data class RepoListing(
    val id: Int?,
    val githubRepoName: String,
    val repoOwnerName: String,
    val projectUrl: String?,
    val ownerImageLink: String?,
    val description: String?,
    val contributorsURL: String?,
)
