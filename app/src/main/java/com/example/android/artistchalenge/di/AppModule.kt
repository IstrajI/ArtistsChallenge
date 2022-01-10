package com.example.android.artistchalenge.di

import android.app.Application
import androidx.room.Room
import com.apollographql.apollo3.ApolloClient
import com.example.android.artistchalenge.data.db.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {
    @Provides
    @Singleton
    fun provideApplication(): Application {
        return application
    }

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
        return Room.databaseBuilder(application, AppDatabase::class.java, DATABASE_NAME)
            .build()
    }

    companion object {
        private const val BASE_URL = "https://graphbrainz.herokuapp.com/"
        private const val DATABASE_NAME = "artist_challenge_db"
    }
}