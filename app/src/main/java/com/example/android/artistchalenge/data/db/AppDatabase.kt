package com.example.android.artistchalenge.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.android.artistchalenge.data.models.ArtistDBModel

@Database(entities = [ArtistDBModel::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun artistDao(): ArtistDao
}