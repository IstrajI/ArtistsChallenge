package com.example.android.artistchalenge.data.models

data class Paginated<T: Any>(
    val data: T,
    val hasMorePages: Boolean?,
    val pageId: String?
)