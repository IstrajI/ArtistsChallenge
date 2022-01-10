package com.example.android.artistchalenge.data.models

data class Artist(
    val mbidId: String,
    val bookmarkId: Long? = null,
    val name: String,
    val image: String? = null,
    val type: String? = null,
    val groupMembers: List<String>? = null,
    val biography: String? = null,
    val listeners: Double? = null,
    val country: String? = null,
    val lifeStart: String? = null,
    val lifeEnd: String? = null,
    var isBookmarked: Boolean = false
)