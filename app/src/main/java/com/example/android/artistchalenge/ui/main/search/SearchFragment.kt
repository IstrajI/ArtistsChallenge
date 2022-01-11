package com.example.android.artistchalenge.ui.main.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.artistchalenge.databinding.FragmentSearchBinding
import com.example.android.artistchalenge.di.ViewModelFactory
import com.example.android.artistchalenge.ui.ArtistChallengeApplication
import com.example.android.artistchalenge.ui.main.MainActivityNavigationListener
import javax.inject.Inject

class SearchFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by viewModels<SearchViewModel> { viewModelFactory }

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    lateinit var artistAdapter: SearchAdapter

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

        initStates()
        initResultsList()
        initSearchView()
    }

    private fun initResultsList() {
        artistAdapter = SearchAdapter()
        artistAdapter.clickListener = (activity as MainActivityNavigationListener)
        binding.searchResultList.adapter = artistAdapter
        viewModel.artists.observe(this) {
            if (it.isNullOrEmpty()) return@observe
            artistAdapter.updateItems(it)
        }

        binding.searchResultList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastVisibleItemPosition =
                    (binding.searchResultList.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                viewModel.onScrolled(lastVisibleItemPosition)
            }
        })
    }

    private fun initSearchView() {
        binding.searchView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s?.length ?: 0 < 3) return
                val searchQuery = s.toString()
                viewModel.searchQuery.value = searchQuery
                viewModel.onSearchInput(searchQuery)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun initStates() {
        viewModel.screenState.observe(this) {
            when (it) {
                SearchViewModel.States.LOADING -> {
                    binding.progressBar.isInvisible = false
                }
                SearchViewModel.States.DEFAULT -> {
                    binding.progressBar.isInvisible = true
                }
                SearchViewModel.States.ERROR -> {
                    binding.progressBar.isInvisible = true
                }
                SearchViewModel.States.NO_ARTISTS -> {
                    binding.progressBar.isInvisible = true
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}