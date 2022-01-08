package com.example.android.artistchalenge.ui.main.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.android.artistchalenge.databinding.FragmentSearchBinding
import com.example.android.artistchalenge.di.ViewModelFactory
import com.example.android.artistchalenge.ui.ArtistChallengeApplication
import javax.inject.Inject

class SearchFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by viewModels<SearchViewModel> { viewModelFactory }


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

        (activity?.application as ArtistChallengeApplication).getComponent().inject(this)

        initSearchView()
        initResultsList()
    }

    private fun initResultsList() {
        binding.searchResultList.adapter = artistAdapter
        viewModel.artists.observe(this) {
            if (it.isNullOrEmpty()) return@observe
            artistAdapter.updateItems(it)
        }
    }

    private fun initSearchView() {
        binding.searchView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s?.length ?: 0 < 3) return
                val searchQuery = s.toString()
                viewModel.searchQuery.value = searchQuery
                viewModel.searchArtists(searchQuery)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}