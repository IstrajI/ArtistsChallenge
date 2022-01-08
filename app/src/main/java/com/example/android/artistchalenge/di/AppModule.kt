package com.example.android.artistchalenge.di

import com.apollographql.apollo3.ApolloClient
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    @Singleton
    fun provideApolloClient(): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl(BASE_URL)
            .build()
    }

    companion object {
        private const val BASE_URL = "https://graphbrainz.herokuapp.com/"
    }
}