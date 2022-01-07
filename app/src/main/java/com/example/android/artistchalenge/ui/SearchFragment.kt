package com.example.android.artistchalenge.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import app.src.main.graphql.com.example.android.artistchalenge.ArtistsQuery
import com.apollographql.apollo3.ApolloClient
import com.example.android.artistchalenge.dao.models.Artist
import com.example.android.artistchalenge.databinding.FragmentSearchBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val artistAdapter = SearchAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSearchList()
    }

    private fun initSearchList() {
        binding.searchArtistList.adapter = artistAdapter

        val apolloClient = ApolloClient.Builder()
            .serverUrl("https://graphbrainz.herokuapp.com/")
            .build()


        lifecycleScope.launch {
            val data: List<Artist>?
            withContext(Dispatchers.IO) {
                val response = apolloClient.query(ArtistsQuery()).execute()
                data = response.data?.search?.artists?.nodes?.map { artistNode ->
                    artistNode?.artistBasicFragment?.let {
                        Artist(image = it.theAudioDB?.logo as? String,
                            name = it.name,
                            type = it.type)
                    }
                }?.filterNotNull()
            }
            artistAdapter.updateItems(data!!)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}