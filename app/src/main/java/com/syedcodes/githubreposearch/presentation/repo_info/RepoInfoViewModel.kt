package com.syedcodes.githubreposearch.presentation.repo_info

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syedcodes.githubreposearch.domain.repository.ContributorRepository
import com.syedcodes.githubreposearch.util.GenericResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepoInfoViewModel @Inject constructor(
    private val contributorRepository: ContributorRepository
) : ViewModel() {

    var state by mutableStateOf(RepoInfoState())

    fun onEvent(event: RepoInfoEvent) {
        when (event) {
            is RepoInfoEvent.LoadContributors -> {
                loadContributors(event.url)
            }
        }
    }

    private fun loadContributors(url: String) {
        viewModelScope.launch {
            contributorRepository.getContributors(url).collect { result ->
                when(result) {
                    is GenericResponse.Error -> {
//                        state = state.copy(error = result.message ?: "")
                    }
                    is GenericResponse.Loading -> {
                        state = state.copy(isLoading = result.isLoading)
                    }
                    is GenericResponse.Success -> {
                        state = state.copy(contributors = result.data ?: emptyList())
                    }
                }
            }
        }
    }

}