package com.example.android.artistchalenge.domain.artist

import com.example.android.artistchalenge.data.repositories.EmptyResultException
import com.example.android.artistchalenge.data.repositories.NetworkErrorException
import com.example.android.artistchalenge.data.repositories.Outcome
import com.example.android.artistchalenge.data.repositories.artist.ArtistRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ArtistInteractor @Inject constructor(private val artistRepository: ArtistRepository) {
    private var hasArtistsToLoad = false
    private var lastArtistSearchPageId = ""
    private var lastSearchName = ""

    fun canLoadMore() = hasArtistsToLoad

    suspend fun searchArtists(name: String): Outcome<List<Artist>> {
        return loadArtists(name)
    }

    suspend fun loadMoreArtists(): Outcome<List<Artist>> {
        return loadArtists(lastSearchName, lastArtistSearchPageId)
    }

    private suspend fun loadArtists(
        name: String,
        lastPageId: String? = null
    ): Outcome<List<Artist>> {
        return try {
            val result = artistRepository.loadArtists(
                name = name,
                lastArtistSearchPageId = lastPageId,
                amount = DEFAULT_ARTISTS_AMOUNT
            )
            hasArtistsToLoad = result.hasMorePages ?: false
            lastSearchName = name
            lastArtistSearchPageId = result.pageId ?: ""
            Outcome.SuccessOutcome(result.data)
        } catch (exception: NetworkErrorException) {
            Outcome.ErrorOutcome(exception.localizedMessage)
        } catch (exception: EmptyResultException) {
            Outcome.ErrorOutcome(EMPTY_RESULT)
        }
    }

    suspend fun loadDetailArtistInfo(id: String): Outcome<Artist> {
        return try {
            val response = artistRepository.loadDetailArtistInfo(id)
            Outcome.SuccessOutcome(response)
        } catch (exception: NetworkErrorException) {
            Outcome.ErrorOutcome(exception.localizedMessage)
        } catch (exception: EmptyResultException) {
            Outcome.ErrorOutcome(EMPTY_RESULT)
        }
    }

    suspend fun saveArtistBookmark(artist: Artist) {
        withContext(Dispatchers.IO) {
            artistRepository.saveArtist(artist)
        }
    }

    fun loadBookmarkedArtists(): Flow<List<Artist>> {
        return artistRepository.subscribeToBookmarks()
    }

    suspend fun removeArtistBookmark(id: Long) {
        withContext(Dispatchers.IO) {
            artistRepository.removeArtistBookmark(id)
        }
    }

    companion object {
        const val DEFAULT_ARTISTS_AMOUNT = 15
        const val EMPTY_RESULT = "empty result"
    }
}