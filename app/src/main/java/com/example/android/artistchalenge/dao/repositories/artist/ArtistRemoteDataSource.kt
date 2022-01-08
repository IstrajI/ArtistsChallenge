package com.example.android.artistchalenge.dao.repositories.artist

import app.src.main.graphql.com.example.android.artistchalenge.ArtistsQuery
import app.src.main.graphql.com.example.android.artistchalenge.fragment.ArtistBasicFragment
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.exception.ApolloException
import com.example.android.artistchalenge.dao.models.Artist
import com.example.android.artistchalenge.dao.repositories.Response
import javax.inject.Inject

class ArtistRemoteDataSource @Inject constructor(private val api: ApolloClient) {

    suspend fun searchArtists(name: String): Response<List<Artist>?> {
        return try {
            val response = api.query(ArtistsQuery(query = name)).execute()
            val artists = response.data?.search?.artists?.nodes?.mapNotNull { artistNode ->
                artistNode?.artistBasicFragment?.let {
                    Artist(
                        image = getLogoUrl(it),
                        name = it.name,
                        type = it.type
                    )
                }
            }
            Response.SuccessResponse(artists!!)
        } catch (exception: ApolloException) {
            Response.ErrorResponse(exception.message ?: "error")
        }
    }

    fun getLogoUrl(artist: ArtistBasicFragment): String? {
        val discogsImagesList = artist.discogs?.images
        val audioDbImage = artist.theAudioDB?.logo
        val fanArtLogoImagesList = artist.fanArt?.logos
        val fanArtBackgroundImagesList = artist.fanArt?.backgrounds

        return when {
            !discogsImagesList.isNullOrEmpty() -> {
                (discogsImagesList[0].url as String)
            }
            audioDbImage != null -> {
                (audioDbImage as String)
            }
            !fanArtLogoImagesList.isNullOrEmpty() && fanArtLogoImagesList[0]?.url != null -> {
                (fanArtLogoImagesList[0]?.url as String)
            }
            !fanArtBackgroundImagesList.isNullOrEmpty() &&
                    fanArtBackgroundImagesList[0]?.url != null -> {
                (fanArtBackgroundImagesList[0]?.url as String)
            }
            else -> null
        }
    }
}