package com.syedcodes.githubreposearch.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocalEntity(
    @PrimaryKey val id: Int? = null,
    val repoOwnerImageLink: String,
    val githubRepoName: String,
    val repoOwnerName: String,
    val projectUrl: String,
    val description: String,
    val contributorsURL: String,
)
