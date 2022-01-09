package com.example.android.artistchalenge.data.repositories.artist

import app.src.main.graphql.com.example.android.artistchalenge.ArtistDetailsQuery
import app.src.main.graphql.com.example.android.artistchalenge.ArtistsQuery
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.exception.ApolloException
import com.example.android.artistchalenge.data.repositories.Response
import javax.inject.Inject

class ArtistRemoteDataSource @Inject constructor(private val api: ApolloClient) {

    suspend fun loadArtists(
        name: String,
        lastArtistSearchPageId: String?,
        amount: Int?
    ): Response<ArtistsQuery.Artists> {
        return try {
            val response = api.query(
                ArtistsQuery(
                    query = name,
                    first = Optional.presentIfNotNull(amount),
                    after = Optional.presentIfNotNull(lastArtistSearchPageId)
                )
            ).execute()
            val artistsResponse = response.data?.search?.artists
            return if (artistsResponse != null) {
                Response.SuccessResponse(artistsResponse)
            } else {
                Response.ErrorResponse("error")
            }
        } catch (exception: ApolloException) {
            Response.ErrorResponse(exception.message ?: "error")
        }
    }

    suspend fun loadDetailArtistInfo(artistId: String): Response<ArtistDetailsQuery.Artist> {
        return try {
            val response = api.query(
                ArtistDetailsQuery(
                    id = artistId
                )
            ).execute()
            val artistsResponse = response.data?.lookup?.artist
            return if (artistsResponse != null) {
                Response.SuccessResponse(artistsResponse)
            } else {
                Response.ErrorResponse("error")
            }
        } catch (exception: ApolloException) {
            Response.ErrorResponse(exception.message ?: "error")
        }
    }
}