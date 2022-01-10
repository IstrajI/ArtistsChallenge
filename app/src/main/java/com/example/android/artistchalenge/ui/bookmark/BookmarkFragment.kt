package com.example.android.artistchalenge.ui.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.android.artistchalenge.databinding.FragmentBookmarkBinding
import com.example.android.artistchalenge.di.ViewModelFactory
import com.example.android.artistchalenge.ui.ArtistChallengeApplication
import javax.inject.Inject

class BookmarkFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by viewModels<BookmarkViewModel> { viewModelFactory }

    private var _binding: FragmentBookmarkBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity?.application as ArtistChallengeApplication).getComponent().inject(this)

        viewModel.loadBookmarks()
        viewModel.bookmarkArtist.observe(this) {
        }
    }


}