package com.example.android.artistchalenge.ui.main.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.artistchalenge.data.models.Artist
import com.example.android.artistchalenge.data.repositories.Outcome
import com.example.android.artistchalenge.domain.artist.ArtistInteractor
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val artistInteractor: ArtistInteractor) :
    ViewModel() {

    val artists = MutableLiveData<List<Artist>>()
    val searchQuery = MutableLiveData<String>()
    val screenState = MutableLiveData(States.DEFAULT)


    fun onSearchInput(name: String) {
        viewModelScope.launch {
            when (val artistsOutcome = artistInteractor.searchArtists(name)) {
                is Outcome.SuccessOutcome -> {
                    artists.value = artistsOutcome.data
                }
                else -> {

                }
            }
        }
    }

    fun onScrolled(lastVisibleItemPosition: Int) {
        val items = artists.value ?: return
        if (lastVisibleItemPosition > items.size - 1 - THRESHOLD
            && artistInteractor.canLoadMore()
            && screenState.value != States.LOADING
        ) {
            screenState.value = States.LOADING
            viewModelScope.launch {

                when (val moreArtistsOutcome = artistInteractor.loadMoreArtists()) {
                    is Outcome.SuccessOutcome -> {
                        artists.value = artists.value!! + moreArtistsOutcome.data
                    }
                    else -> {

                    }
                }
                screenState.value = States.DEFAULT

            }
        }
    }

    enum class States {
        DEFAULT, LOADING, ERROR, NO_ARTISTS
    }

    companion object {
        const val THRESHOLD = 4
    }
}