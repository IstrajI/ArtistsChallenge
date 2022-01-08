package com.example.android.artistchalenge.dao.repositories.artist

import app.src.main.graphql.com.example.android.artistchalenge.ArtistsQuery
import com.apollographql.apollo3.ApolloClient
import com.example.android.artistchalenge.dao.models.Artist
import javax.inject.Inject

class ArtistRemoteDataSource @Inject constructor(private val api: ApolloClient) {

    suspend fun searchArtists(name: String): List<Artist>? {
        val response = api.query(ArtistsQuery(query = name)).execute()
        return response.data?.search?.artists?.nodes?.mapNotNull { artistNode ->
            artistNode?.artistBasicFragment?.let {
                Artist(
                    image = it.theAudioDB?.logo as? String,
                    name = it.name,
                    type = it.type
                )
            }
        }
    }
}