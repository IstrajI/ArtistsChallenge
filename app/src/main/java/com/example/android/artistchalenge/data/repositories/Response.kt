package com.example.android.artistchalenge.data.repositories

sealed class Response<out T: Any?> {
    data class SuccessResponse<T: Any>(val data: T): Response<T>()
    data class ErrorResponse(val errorMessage: String): Response<Nothing>()
}