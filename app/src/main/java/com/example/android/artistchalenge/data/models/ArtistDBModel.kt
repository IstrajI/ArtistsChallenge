package com.example.android.artistchalenge.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "artist")
data class ArtistDBModel (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "mbid_id")
    val mbid: String? = null,
    @ColumnInfo(name = "image")
    val image: String? = null,
    @ColumnInfo(name = "name")
    val name: String? = null,
    @ColumnInfo(name = "type")
    val type: String? = null,
    @ColumnInfo(name = "biography")
    val biography: String? = null,
    @ColumnInfo(name = "listeners")
    val listeners: Double? = null,
    @ColumnInfo(name = "country")
    val country: String? = null,
    @ColumnInfo(name = "lifeStart")
    val lifeStart: String? = null,
    @ColumnInfo(name = "lifeEnd")
    val lifeEnd: String? = null
)