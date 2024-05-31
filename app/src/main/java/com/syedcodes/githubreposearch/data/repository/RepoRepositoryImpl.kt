package com.syedcodes.githubreposearch.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.syedcodes.githubreposearch.data.local.LocalDatabase
import com.syedcodes.githubreposearch.data.mapper.toRepoEntity
import com.syedcodes.githubreposearch.data.mapper.toRepoListing
import com.syedcodes.githubreposearch.data.remote.GithubApi
import com.syedcodes.githubreposearch.domain.model.GitHubRepositoryItem
import com.syedcodes.githubreposearch.domain.model.RepoListing
import com.syedcodes.githubreposearch.domain.repository.RepoRepository
import com.syedcodes.githubreposearch.util.GenericResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepoRepositoryImpl @Inject constructor(
    private val api: GithubApi,
    private val db: LocalDatabase,
) : RepoRepository {

    private val dao = db.dao
    override suspend fun insert15Repos(repos: List<RepoListing>): Flow<GenericResponse<Unit>> {
        return flow {
            emit(GenericResponse.Loading(true))
            dao.clearRepoListing()
            dao.insertRepoListing(repos.map { it.toRepoEntity() })
            emit(GenericResponse.Success(Unit))
            emit(GenericResponse.Loading(false))
        }
    }

    override suspend fun get15ReposFromCache(): Flow<GenericResponse<List<RepoListing>>> {
        return flow {
            emit(GenericResponse.Loading(true))
            val localListing = dao.getRepoListing()
            emit(GenericResponse.Success(
                data = localListing.map { it.toRepoListing() }
            ))
            emit(GenericResponse.Loading(false))
        }
    }

    override suspend fun getReposFromNetwork(
        query: String,
    ): Flow<PagingData<GitHubRepositoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                initialLoadSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                RepoPagingSource(api, query)
            }
        ).flow
    }
}