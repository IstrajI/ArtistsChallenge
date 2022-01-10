package com.example.android.artistchalenge.data.repositories.artist

import com.example.android.artistchalenge.data.db.AppDatabase
import com.example.android.artistchalenge.data.models.ArtistDBModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class ArtistLocalDataSource @Inject constructor(db: AppDatabase) {
    private val artistDao = db.artistDao()

    fun saveArtist(artist: ArtistDBModel) {
        artistDao.insert(artist)
    }

    fun loadBookmarkedArtist(): Flow<List<ArtistDBModel>> {
        return artistDao.getAll()
    }
}