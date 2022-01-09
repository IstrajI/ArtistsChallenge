package com.example.android.artistchalenge.data.repositories.artist

import app.src.main.graphql.com.example.android.artistchalenge.ArtistsQuery
import com.example.android.artistchalenge.data.repositories.Response
import javax.inject.Inject

class ArtistRepository @Inject constructor(
    private val artistRemoteDataSource: ArtistRemoteDataSource
) {
    suspend fun loadArtists(name: String, lastArtistSearchPageId: String?, amount: Int): Response<ArtistsQuery.Artists> {
        return artistRemoteDataSource.loadArtists(name, lastArtistSearchPageId, amount)
    }
}