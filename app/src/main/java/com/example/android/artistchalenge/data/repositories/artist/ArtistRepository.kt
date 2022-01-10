package com.example.android.artistchalenge.data.repositories.artist

import app.src.main.graphql.com.example.android.artistchalenge.ArtistDetailsQuery
import app.src.main.graphql.com.example.android.artistchalenge.ArtistsQuery
import com.example.android.artistchalenge.data.models.Artist
import com.example.android.artistchalenge.data.models.ArtistDBModel
import com.example.android.artistchalenge.data.repositories.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ArtistRepository @Inject constructor(
    private val artistRemoteDataSource: ArtistRemoteDataSource,
    private val artistLocalDataSource: ArtistLocalDataSource
) {
    suspend fun loadArtists(
        name: String,
        lastArtistSearchPageId: String?,
        amount: Int
    ): Response<ArtistsQuery.Artists> {
        return artistRemoteDataSource.loadArtists(name, lastArtistSearchPageId, amount)
    }

    suspend fun loadDetailArtistInfo(artistId: String): Response<ArtistDetailsQuery.Artist> {
        return artistRemoteDataSource.loadDetailArtistInfo(artistId)
    }

    fun saveArtist(artist: Artist) {
        artistLocalDataSource.saveArtist(
            ArtistDBModel(
                mbid = artist.id,
                name = artist.name
            )
        )
    }

    fun loadBookmarkedArtist(): Flow<List<Artist>> {
        val dbArtists = artistLocalDataSource.loadBookmarkedArtist()
        return dbArtists.map { artists ->
            artists.map { artist ->
                Artist(
                    id = artist.mbid,
                    name = artist.name
                )
            }
        }
    }
}