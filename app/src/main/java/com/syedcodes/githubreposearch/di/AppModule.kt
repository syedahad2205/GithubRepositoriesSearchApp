package com.syedcodes.githubreposearch.di

import android.app.Application
import androidx.room.Room
import com.syedcodes.githubreposearch.data.local.LocalDatabase
import com.syedcodes.githubreposearch.data.remote.ContributorApi
import com.syedcodes.githubreposearch.data.remote.GithubApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGithubApi(): GithubApi {
        return Retrofit.Builder()
            .baseUrl(GithubApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun providesContributorsApi(): ContributorApi {
        return Retrofit.Builder()
            .baseUrl(GithubApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(ContributorApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRepoDatabase(app: Application): LocalDatabase {
        return Room.databaseBuilder(
            app,
            LocalDatabase::class.java,
            "github_repo_db"
        ).build()
    }

}