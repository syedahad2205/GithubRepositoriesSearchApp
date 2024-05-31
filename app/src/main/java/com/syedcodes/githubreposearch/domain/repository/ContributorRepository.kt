package com.syedcodes.githubreposearch.domain.repository

import com.syedcodes.githubreposearch.domain.model.Contributor
import com.syedcodes.githubreposearch.util.GenericResponse
import kotlinx.coroutines.flow.Flow

interface ContributorRepository {
    suspend fun getContributors(url: String): Flow<GenericResponse<List<Contributor>>>
}