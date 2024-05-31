package com.syedcodes.githubreposearch.util

sealed class GenericResponse<T>(val data: T? = null, message: String? = null) {
    class Success<T>(data: T?) : GenericResponse<T>(data)
    class Error<T>(message: String, data: T? = null) : GenericResponse<T>(data, message)
    class Loading<T>(val isLoading: Boolean = true): GenericResponse<T>(null)
}