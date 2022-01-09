package com.example.android.artistchalenge.data.models

data class Artist(
    val id: String? = null,
    val image: String? = null,
    val name: String? = null,
    val type: String? = null,

    val groupMembers: List<String>? = null,
    val biography: String? = null,
    val listeners: Double? = null,
    val country: String? = null,
    val lifeStart: String? = null,
    val lifeEnd: String? = null
)