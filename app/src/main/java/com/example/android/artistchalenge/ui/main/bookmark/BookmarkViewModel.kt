package com.example.android.artistchalenge.ui.main.bookmark

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.artistchalenge.domain.artist.Artist
import com.example.android.artistchalenge.domain.artist.ArtistInteractor
import kotlinx.coroutines.launch
import javax.inject.Inject

class BookmarkViewModel @Inject constructor(private val artistInteractor: ArtistInteractor) :
    ViewModel() {
    val bookmarkArtist = MutableLiveData<List<Artist>>()

    fun loadBookmarks() {
        viewModelScope.launch {
            artistInteractor.loadBookmarkedArtists().collect {
                bookmarkArtist.value = it
            }
        }
    }
}