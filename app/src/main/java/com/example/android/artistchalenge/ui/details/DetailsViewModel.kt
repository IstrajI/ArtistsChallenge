package com.example.android.artistchalenge.ui.details

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.android.artistchalenge.R
import com.example.android.artistchalenge.domain.artist.Artist
import com.example.android.artistchalenge.data.repositories.Outcome
import com.example.android.artistchalenge.domain.artist.ArtistInteractor
import com.example.android.artistchalenge.ui.ArtistChallengeApplication
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToInt

class DetailsViewModel @Inject constructor(
    app: Application,
    private val artistInteractor: ArtistInteractor
) : AndroidViewModel(app) {
    private val resources = getApplication<Application>().resources!!
    val artistUIModel = MutableLiveData<ArtistDetailsUIModel>()
    var artist: Artist? = null
    val screenState = MutableLiveData(DetailsActivity.States.DEFAULT)

    fun loadArtistDetails(artistId: String) {
        screenState.value = DetailsActivity.States.LOADING
        viewModelScope.launch {
            when (val artistDetailsOutcome = artistInteractor.loadDetailArtistInfo(artistId)) {
                is Outcome.SuccessOutcome -> {
                    artist = artistDetailsOutcome.data
                    artistDetailsOutcome.data.let {
                        artistUIModel.value = ArtistDetailsUIModel(
                            name = it.name,
                            image = it.image,
                            description = it.type,
                            info = formatDescription(it),
                            groupMembers = formatGroupMembers(it.groupMembers),
                            biography = it.biography,
                            isBookmarked = it.isBookmarked,
                        )
                    }
                }
                is Outcome.ErrorOutcome -> {
                    Toast.makeText(
                        getApplication<ArtistChallengeApplication>().applicationContext,
                        artistDetailsOutcome.errorMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            screenState.value = DetailsActivity.States.DEFAULT
        }
    }

    private fun formatGroupMembers(members: List<String>?): String? {
        return members?.joinToString(separator = resources.getString(R.string.dotSpacedSymbolSeparator))
    }

    private fun formatDescription(artist: Artist): String? {
        val infoArray = arrayOf(
            formatDates(artist.lifeStart, artist.lifeEnd),
            artist.country,
            formatListeners(artist.listeners)
        ).filterNotNull()

        if (infoArray.count() == 0) return null
        return infoArray.joinToString(separator = resources.getString(R.string.dotSpacedSymbolSeparator))
    }

    private fun formatListeners(lastFmListeners: Double?): String? {
        val listenersTitle = resources.getString(R.string.detailsListenersLastFmTitle)
        return lastFmListeners?.let { "${lastFmListeners.roundToInt()} $listenersTitle" }
    }

    private fun formatDates(begin: String?, end: String?): String? {
        val nowTitle = resources.getString(R.string.detailsNowTitle)
        return when {
            begin != null && end != null -> return "$begin - $end"
            begin != null && end == null -> return "$begin - $nowTitle"
            else -> null
        }
    }

    fun onBookmarkClicked() {
        val isBookmarked = artistUIModel.value?.isBookmarked ?: return
        artistUIModel.value?.isBookmarked = isBookmarked.not()
        artistUIModel.value = artistUIModel.value

        viewModelScope.launch {
            if (artistUIModel.value?.isBookmarked == true) {
                artistInteractor.saveArtistBookmark(artist!!)
            } else {
                artist?.bookmarkId?.let { artistInteractor.removeArtistBookmark(it) }
            }
        }
    }
}