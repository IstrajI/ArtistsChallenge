package com.example.android.artistchalenge.data.repositories

class NetworkErrorException(override val message: String?) : Exception()
class EmptyResultException: Exception()