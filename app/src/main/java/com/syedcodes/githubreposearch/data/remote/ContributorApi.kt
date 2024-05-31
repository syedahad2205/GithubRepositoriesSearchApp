package com.syedcodes.githubreposearch.data.remote

import com.syedcodes.githubreposearch.domain.model.Contributor
import retrofit2.http.GET
import retrofit2.http.Url

interface ContributorApi {
    @GET
    suspend fun getContributors(@Url url: String): List<Contributor>
}