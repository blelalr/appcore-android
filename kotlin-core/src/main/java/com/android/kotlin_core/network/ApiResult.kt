package com.android.kotlin_core.network

import com.android.kotlin_core.model.BaseErrorData

sealed class ApiResult<out T> {
    data class Success<out T >(val data: T) : ApiResult<T>()
    data class Error(val errorData: BaseErrorData) : ApiResult<Nothing>()
    data class Exception(val exception: java.lang.Exception) : ApiResult<Nothing>()
}