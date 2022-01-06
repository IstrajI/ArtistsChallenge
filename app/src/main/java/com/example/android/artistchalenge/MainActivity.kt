package com.example.android.artistchalenge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import app.src.main.graphql.com.example.android.artistchalenge.ArtistQuery
import com.apollographql.apollo3.ApolloClient
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val apolloClient = ApolloClient.Builder()
            .serverUrl("https://graphbrainz.herokuapp.com/")
            .build()

        MainScope().launch {
            val response = apolloClient.query(ArtistQuery()).execute()
            Log.d("TestPish", "response ${response.data?.node}")
        }
    }
}