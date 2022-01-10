package com.example.android.artistchalenge.data.repositories.artist

import app.src.main.graphql.com.example.android.artistchalenge.ArtistDetailsQuery
import app.src.main.graphql.com.example.android.artistchalenge.ArtistsQuery
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.exception.ApolloException
import com.example.android.artistchalenge.data.repositories.NetworkErrorException
import javax.inject.Inject

class ArtistRemoteDataSource @Inject constructor(private val api: ApolloClient) {

    suspend fun loadArtists(
        name: String,
        lastArtistSearchPageId: String?,
        amount: Int?
    ): ArtistsQuery.Artists {
        try {
            val response = api.query(
                ArtistsQuery(
                    query = name,
                    first = Optional.presentIfNotNull(amount),
                    after = Optional.presentIfNotNull(lastArtistSearchPageId)
                )
            ).execute()
            return response.dataAssertNoErrors.search?.artists
                ?: throw NetworkErrorException("empty result")
        } catch (exception: ApolloException) {
            throw NetworkErrorException(exception.localizedMessage)
        }
    }

    suspend fun loadDetailArtistInfo(artistId: String): ArtistDetailsQuery.Artist {
        try {
            val response = api.query(ArtistDetailsQuery(id = artistId)).execute()
            return response.dataAssertNoErrors.lookup?.artist
                ?: throw NetworkErrorException("empty result")
        } catch (exception: ApolloException) {
            throw NetworkErrorException(exception.localizedMessage)
        }
    }
}