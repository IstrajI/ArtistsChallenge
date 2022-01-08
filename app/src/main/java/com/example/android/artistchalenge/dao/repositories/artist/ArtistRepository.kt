package com.example.android.artistchalenge.dao.repositories.artist

import com.example.android.artistchalenge.dao.models.Artist
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ArtistRepository @Inject constructor(
    private val artistRemoteDataSource: ArtistRemoteDataSource
) {
    suspend fun searchArtists(name: String): List<Artist>? {
        return withContext(Dispatchers.IO) {
            artistRemoteDataSource.searchArtists(name)
        }
    }
}