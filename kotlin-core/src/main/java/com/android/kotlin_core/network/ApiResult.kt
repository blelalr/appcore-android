package com.android.kotlin_core.network

sealed class ApiResult<out T> {
    data class Success<out T >(val data: T) : ApiResult<T>()
    data class Error(val statusCode: Int, val errorMsg: String) : ApiResult<Nothing>()
    data class Exception(val exception: java.lang.Exception) : ApiResult<Nothing>()
}