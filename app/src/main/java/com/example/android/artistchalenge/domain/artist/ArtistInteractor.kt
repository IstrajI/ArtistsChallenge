package com.example.android.artistchalenge.domain.artist

import app.src.main.graphql.com.example.android.artistchalenge.ArtistsQuery
import app.src.main.graphql.com.example.android.artistchalenge.fragment.ArtistModel
import com.example.android.artistchalenge.data.models.Artist
import com.example.android.artistchalenge.data.repositories.Response
import com.example.android.artistchalenge.data.repositories.artist.ArtistRepository
import javax.inject.Inject

class ArtistInteractor @Inject constructor(private val artistRepository: ArtistRepository) {
    private var hasArtistsToLoad = false
    private var lastArtistSearchPageId = ""
    private var lastSearchName = ""

    fun canLoadMore() = hasArtistsToLoad

    suspend fun searchArtists(name: String): List<Artist> {
        val artistsData = loadArtistNodes(name)
        return mapArtistsNodeToArtist(artistsData)
    }

    suspend fun loadMoreArtists(): List<Artist> {
        val artistsData = loadArtistNodes(lastSearchName, lastArtistSearchPageId)
        return mapArtistsNodeToArtist(artistsData)
    }

    private suspend fun loadArtistNodes(
        name: String,
        lastPageId: String? = null
    ): List<ArtistsQuery.Node?>? {
        return when (val response = artistRepository.loadArtists(
            name = name,
            lastArtistSearchPageId = lastPageId,
            amount = DEFAULT_ARTISTS_AMOUNT
        )) {
            is Response.SuccessResponse -> {
                val result = response.data
                hasArtistsToLoad = result.pageInfo.hasNextPage
                lastSearchName = name
                lastArtistSearchPageId = result.pageInfo.endCursor ?: ""
                return result.nodes
            }
            is Response.ErrorResponse -> null
        }
    }

    private fun mapArtistsNodeToArtist(artistsNodes: List<ArtistsQuery.Node?>?): List<Artist> {
        if (artistsNodes == null) return emptyList()
        return artistsNodes.filterNotNull()
            .map {
                Artist(
                    name = it.artistModel.name,
                    type = it.artistModel.type,
                    image = getLogoUrl(it.artistModel)
                )
            }
    }

    private fun getLogoUrl(artist: ArtistModel): String? {
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

    companion object {
        const val DEFAULT_ARTISTS_AMOUNT = 15
    }
}