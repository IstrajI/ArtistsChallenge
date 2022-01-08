package com.example.android.artistchalenge.dao.repositories

sealed class Response<out T: Any?> {
    data class SuccessResponse<T: Any>(val response: T): Response<T>()
    data class ErrorResponse(val errorMessage: String): Response<Nothing>()
}