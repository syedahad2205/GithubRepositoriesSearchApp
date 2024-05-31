package com.syedcodes.githubreposearch.data.repository

import com.syedcodes.githubreposearch.data.remote.ContributorApi
import com.syedcodes.githubreposearch.domain.model.Contributor
import com.syedcodes.githubreposearch.domain.repository.ContributorRepository
import com.syedcodes.githubreposearch.util.GenericResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ContributorRepositoryImpl @Inject constructor(
    private val api: ContributorApi
) : ContributorRepository {
    override suspend fun getContributors(url: String): Flow<GenericResponse<List<Contributor>>> {
        return flow {
            emit(GenericResponse.Loading())
            val contributors = try {
                api.getContributors(url)
            } catch (e: Exception) {
                e.printStackTrace()
                emit(GenericResponse.Error(message = e.localizedMessage ?: "Unknown error"))
                null
            }
            contributors?.let {
                emit(GenericResponse.Success(
                    data = it
                ))
            }
            emit(GenericResponse.Loading(isLoading = false))
        }
    }
}