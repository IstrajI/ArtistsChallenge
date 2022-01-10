package com.example.android.artistchalenge.data.repositories.artist

import com.example.android.artistchalenge.data.db.AppDatabase
import com.example.android.artistchalenge.data.models.ArtistDBModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArtistLocalDataSource @Inject constructor(db: AppDatabase) {
    private val artistDao = db.artistDao()
    val bookmarks = MutableStateFlow<List<ArtistDBModel>>(mutableListOf())

    init {
        CoroutineScope(Dispatchers.IO).launch {
            artistDao.getAll().collect {
                bookmarks.value = it
            }
        }
    }

    fun saveArtist(artist: ArtistDBModel) {
        bookmarks.value = (bookmarks.value + artist)
        artistDao.insert(artist)
    }

    fun removeBookmarkedArtist(id: Long) {
        val bookmarkToRemove = bookmarks.value.find {
            it.id == id
        } ?: return
        bookmarks.value = (bookmarks.value - bookmarkToRemove)
        artistDao.deleteById(id)
    }
}