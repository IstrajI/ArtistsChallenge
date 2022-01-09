package com.example.android.artistchalenge.domain.artist

import app.src.main.graphql.com.example.android.artistchalenge.ArtistDetailsQuery
import app.src.main.graphql.com.example.android.artistchalenge.ArtistsQuery
import app.src.main.graphql.com.example.android.artistchalenge.fragment.ArtistDetailedModel
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

    suspend fun loadDetailArtistInfo(id: String): Artist? {
        return when (val response = artistRepository.loadDetailArtistInfo(id)) {
            is Response.SuccessResponse -> {
                return mapArtistDetailsResponseToArtist(response.data)
            }
            is Response.ErrorResponse -> null
        }
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

    private fun mapArtistDetailsResponseToArtist(artistDetailsResponse: ArtistDetailsQuery.Artist): Artist {
        return artistDetailsResponse.let {
            Artist(
                id = it.artistModel.mbid as String?,
                image = getLogoUrl(it.artistModel),
                name = it.artistModel.name,
                type = it.artistModel.type,
                groupMembers = getGroupMembers(it.artistDetailedModel),
                biography = getBiography(it.artistDetailedModel),
                listeners = it.artistDetailedModel.lastFM?.listenerCount,
                country = it.artistDetailedModel.country,
                lifeStart = it.artistDetailedModel.lifeSpan?.begin as String?,
                lifeEnd = it.artistDetailedModel.lifeSpan?.end as String?
            )
        }
    }

    private fun mapArtistsNodeToArtist(artistsNodes: List<ArtistsQuery.Node?>?): List<Artist> {
        if (artistsNodes == null) return emptyList()
        return artistsNodes.filterNotNull()
            .map {
                Artist(
                    id = it.artistModel.mbid as String?,
                    name = it.artistModel.name,
                    type = it.artistModel.type,
                    image = getLogoUrl(it.artistModel)
                )
            }
    }

    private fun getGroupMembers(artist: ArtistDetailedModel): List<String>? {
        return artist.discogs?.members?.map { member ->
            member.name
        }
    }

    private fun getBiography(artist: ArtistDetailedModel): String? {
        val audioDbBiography = artist.theAudioDB?.biography
        val lastFmSummary = artist.lastFM?.biography?.summaryHTML
        val lastFmBiography = artist.lastFM?.biography?.contentHTML
        return when {
            audioDbBiography != null -> audioDbBiography
            lastFmSummary != null -> lastFmSummary
            lastFmBiography != null -> lastFmBiography
            else -> null
        }
    }

    private fun getLogoUrl(artist: ArtistModel): String? {
        val discogsImage = artist.discogs?.images?.elementAtOrNull(0)?.url as String?
        val audioDbImage = artist.theAudioDB?.logo as String?
        val fanArtLogo = artist.fanArt?.logos?.elementAtOrNull(0)?.url as String?
        val fanArtBackground = artist.fanArt?.backgrounds?.elementAtOrNull(0)?.url as String?

        return when {
            !discogsImage.isNullOrEmpty() -> discogsImage
            !audioDbImage.isNullOrEmpty() -> audioDbImage
            !fanArtLogo.isNullOrEmpty() -> fanArtLogo
            !fanArtBackground.isNullOrEmpty() -> fanArtBackground
            else -> null
        }
    }

    companion object {
        const val DEFAULT_ARTISTS_AMOUNT = 15
    }
}