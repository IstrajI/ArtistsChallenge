package com.example.android.artistchalenge.ui.main.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.artistchalenge.dao.models.Artist
import com.example.android.artistchalenge.dao.repositories.Response
import com.example.android.artistchalenge.dao.repositories.artist.ArtistRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val artistRepository: ArtistRepository): ViewModel() {

    val artists = MutableLiveData<List<Artist>?>()
    val searchQuery = MutableLiveData<String>()

    fun searchArtists(name: String) {
        viewModelScope.launch {
            val response = artistRepository.searchArtists(name)
            artists.value = when (response) {
                is Response.SuccessResponse -> response.response
                is Response.ErrorResponse -> null
            }
        }
    }
}