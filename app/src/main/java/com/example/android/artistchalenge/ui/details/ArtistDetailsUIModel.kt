package com.example.android.artistchalenge.ui.details

data class ArtistDetailsUIModel(
    val name: String? = null,
    val image: String? = null,
    val description: String? = null,
    val info: String? = null,
    val groupMembers: String? = null,
    val biography: String? = null,
    var isBookmarked: Boolean = false
)