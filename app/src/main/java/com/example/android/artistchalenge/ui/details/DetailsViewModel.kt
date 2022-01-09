package com.example.android.artistchalenge.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.artistchalenge.data.models.Artist
import com.example.android.artistchalenge.domain.artist.ArtistInteractor
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsViewModel @Inject constructor(private val artistInteractor: ArtistInteractor) :
    ViewModel() {
    val artist = MutableLiveData<Artist>()

    fun loadArtistDetails(artistId: String?) {
        artistId ?: return
        viewModelScope.launch {
            val artistDetails = artistInteractor.loadDetailArtistInfo(artistId)
            artistDetails?.let { artist.value = it }
        }
    }
}