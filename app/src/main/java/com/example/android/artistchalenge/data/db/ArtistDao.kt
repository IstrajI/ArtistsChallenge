package com.example.android.artistchalenge.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.android.artistchalenge.data.models.ArtistDBModel
import kotlinx.coroutines.flow.Flow

@Dao
interface ArtistDao {
    @Query("SELECT * FROM artist")
    fun getAll(): Flow<List<ArtistDBModel>>

    @Insert
    fun insert(artist: ArtistDBModel)

    @Query("DELETE FROM artist WHERE id = :bookmarkId")
    fun deleteById(bookmarkId: Long)

    @Delete
    fun delete(artist: ArtistDBModel)
}