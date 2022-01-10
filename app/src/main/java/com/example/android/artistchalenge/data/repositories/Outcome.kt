package com.example.android.artistchalenge.data.repositories

sealed class Outcome<out T: Any?> {
    data class SuccessOutcome<T: Any>(val data: T): Outcome<T>()
    data class ErrorOutcome(val errorMessage: String): Outcome<Nothing>()
}