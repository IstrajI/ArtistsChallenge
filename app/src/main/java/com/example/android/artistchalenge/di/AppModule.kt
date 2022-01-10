package com.example.android.artistchalenge.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.apollographql.apollo3.ApolloClient
import com.example.android.artistchalenge.data.db.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val applicationContext: Context) {
    @Provides
    @Singleton
    fun provideApolloClient(): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl(BASE_URL)
            .build()
    }

    @Provides
    @Singleton
    fun provideRoomDatabase(): AppDatabase {
        return Room.databaseBuilder(applicationContext, AppDatabase::class.java, DATABASE_NAME)
            .build()
    }

    companion object {
        private const val BASE_URL = "https://graphbrainz.herokuapp.com/"
        private const val DATABASE_NAME = "artist_challenge_db"
    }
}