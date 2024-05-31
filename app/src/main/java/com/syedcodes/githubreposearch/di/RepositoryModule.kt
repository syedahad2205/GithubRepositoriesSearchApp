package com.syedcodes.githubreposearch.di

import com.syedcodes.githubreposearch.data.repository.ContributorRepositoryImpl
import com.syedcodes.githubreposearch.data.repository.RepoRepositoryImpl
import com.syedcodes.githubreposearch.domain.repository.ContributorRepository
import com.syedcodes.githubreposearch.domain.repository.RepoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRepoRepository(
        repoRepositoryImpl: RepoRepositoryImpl
    ): RepoRepository

    @Binds
    @Singleton
    abstract fun bindContributorRepository(
        contributorRepositoryImpl: ContributorRepositoryImpl
    ): ContributorRepository

}