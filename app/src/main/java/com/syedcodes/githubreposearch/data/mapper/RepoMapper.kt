package com.syedcodes.githubreposearch.data.mapper

import androidx.paging.PagingData
import com.syedcodes.githubreposearch.data.local.LocalEntity
import com.syedcodes.githubreposearch.domain.model.GitHubRepositoryItem
import com.syedcodes.githubreposearch.domain.model.Owner
import com.syedcodes.githubreposearch.domain.model.RepoListing
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

fun LocalEntity.toRepoListing(): RepoListing {
    return RepoListing(
        id = id,
        githubRepoName = githubRepoName,
        repoOwnerName = repoOwnerName,
        projectUrl = projectUrl,
        ownerImageLink = repoOwnerImageLink,
        description = description,
        contributorsURL = contributorsURL
    )
}

fun RepoListing.toRepoEntity(): LocalEntity {
    return LocalEntity(
        githubRepoName = githubRepoName,
        repoOwnerName = repoOwnerName,
        projectUrl = projectUrl ?: "",
        repoOwnerImageLink = ownerImageLink ?: "",
        description = description ?: "",
        contributorsURL = contributorsURL ?: "",
    )
}

fun GitHubRepositoryItem.toRepoListing(): RepoListing {
    return RepoListing(
        id = id,
        githubRepoName = name,
        repoOwnerName = owner.login,
        projectUrl = html_url,
        ownerImageLink = owner.avatar_url,
        description = description,
        contributorsURL = contributors_url
    )
}

fun RepoListing.toGitHubRepositoryItem(): GitHubRepositoryItem {
    return GitHubRepositoryItem(
        name = githubRepoName,
        owner = Owner(login = repoOwnerName, avatar_url = ownerImageLink ?: ""),
        id = id ?: 0,
        description = description ?: "",
        html_url = projectUrl ?: "",
        contributors_url = contributorsURL ?: ""

    )
}

fun <T : Any> List<T>.toPagingData(): Flow<PagingData<T>> {
    return flowOf(PagingData.from(this))
}