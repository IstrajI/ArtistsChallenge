package com.example.android.artistchalenge.data.repositories.artist

import android.util.Log
import app.src.main.graphql.com.example.android.artistchalenge.ArtistsQuery
import app.src.main.graphql.com.example.android.artistchalenge.fragment.ArtistDetailedModel
import app.src.main.graphql.com.example.android.artistchalenge.fragment.ArtistModel
import com.example.android.artistchalenge.data.models.Artist
import com.example.android.artistchalenge.data.models.ArtistDBModel
import com.example.android.artistchalenge.data.models.Paginated
import com.example.android.artistchalenge.data.repositories.EmptyResultException
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
    ): Paginated<List<Artist>> {
        val artistsSearchResponse =
            artistRemoteDataSource.loadArtists(name, lastArtistSearchPageId, amount)

        return Paginated(
            data = mapArtistsNodeToArtist(artistsSearchResponse.nodes),
            hasMorePages = artistsSearchResponse.pageInfo.hasNextPage,
            pageId = artistsSearchResponse.pageInfo.endCursor
        )
    }

    private fun mapArtistsNodeToArtist(artistsNodes: List<ArtistsQuery.Node?>?): List<Artist> {
        if (artistsNodes == null) return emptyList()
        return artistsNodes.filterNotNull()
            .map {
                Artist(
                    mbidId = it.artistModel.mbid as String,
                    name = it.artistModel.name!!,
                    type = it.artistModel.type,
                    image = getLogoUrl(it.artistModel)
                )
            }
    }

    suspend fun loadDetailArtistInfo(artistId: String): Artist {
        val artistDetails = artistRemoteDataSource.loadDetailArtistInfo(artistId)
        if (artistDetails.artistModel.name == null) throw EmptyResultException()

        var isBookmarked = false
        var bookmarkId: Long? = null
        artistLocalDataSource.bookmarks.value.findLast {
            it.mbid == artistDetails.artistModel.mbid
        }?.let {
            isBookmarked = true
            bookmarkId = it.id
        }

        return Artist(
            mbidId = artistDetails.artistModel.mbid as String,
            image = getLogoUrl(artistDetails.artistModel),
            name = artistDetails.artistModel.name,
            type = artistDetails.artistModel.type,
            groupMembers = getGroupMembers(artistDetails.artistDetailedModel),
            biography = getBiography(artistDetails.artistDetailedModel),
            listeners = artistDetails.artistDetailedModel.lastFM?.listenerCount,
            country = artistDetails.artistDetailedModel.country,
            lifeStart = artistDetails.artistDetailedModel.lifeSpan?.begin as String?,
            lifeEnd = artistDetails.artistDetailedModel.lifeSpan?.end as String?,
            isBookmarked = isBookmarked,
            bookmarkId = bookmarkId
        )
    }

    fun saveArtist(artist: Artist) {
        val artistDBModel = ArtistDBModel(
            mbid = artist.mbidId,
            name = artist.name
        )

        artistLocalDataSource.saveArtist(artistDBModel)
    }

    fun subscribeToBookmarks(): Flow<List<Artist>> {
        return artistLocalDataSource.bookmarks.map { artists ->
            artists.map { artist ->
                Artist(
                    mbidId = artist.mbid!!,
                    bookmarkId = artist.id,
                    name = artist.name!!,
                    type = artist.type

                )
            }
        }
    }

    fun removeArtistBookmark(id: Long) {
        artistLocalDataSource.removeBookmarkedArtist(id)
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
}