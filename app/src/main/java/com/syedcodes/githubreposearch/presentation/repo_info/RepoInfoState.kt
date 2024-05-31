package com.syedcodes.githubreposearch.presentation.repo_info

import com.syedcodes.githubreposearch.domain.model.Contributor

data class RepoInfoState(
    val contributors: List<Contributor> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
