package com.syedcodes.githubreposearch.domain.repository

import androidx.paging.PagingData
import com.syedcodes.githubreposearch.domain.model.GitHubRepositoryItem
import com.syedcodes.githubreposearch.domain.model.RepoListing
import com.syedcodes.githubreposearch.util.GenericResponse
import kotlinx.coroutines.flow.Flow


interface RepoRepository {

    suspend fun insert15Repos(repos: List<RepoListing>): Flow<GenericResponse<Unit>>

    suspend fun get15ReposFromCache(): Flow<GenericResponse<List<RepoListing>>>

    suspend fun getReposFromNetwork(
        query: String,
    ): Flow<PagingData<GitHubRepositoryItem>>
}