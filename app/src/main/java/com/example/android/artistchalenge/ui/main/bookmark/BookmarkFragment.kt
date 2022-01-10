package com.example.android.artistchalenge.ui.main.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.android.artistchalenge.databinding.FragmentBookmarkBinding
import com.example.android.artistchalenge.di.ViewModelFactory
import com.example.android.artistchalenge.ui.ArtistChallengeApplication
import com.example.android.artistchalenge.ui.main.MainActivityNavigationListener
import javax.inject.Inject

class BookmarkFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by viewModels<BookmarkViewModel> { viewModelFactory }

    private var _binding: FragmentBookmarkBinding? = null
    private val binding get() = _binding!!

    var mainActivityNavigationListener: MainActivityNavigationListener? = null
    lateinit var bookmarkAdapter: BookmarkAdapter

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

        binding.bookmarkList.layoutManager = GridLayoutManager(activity, GRID_COLUMN_AMOUNT)
        bookmarkAdapter = BookmarkAdapter()
        bookmarkAdapter.clickListener = mainActivityNavigationListener
        binding.bookmarkList.adapter = bookmarkAdapter

        viewModel.loadBookmarks()
        viewModel.bookmarkArtist.observe(this) {
            if (it.isNullOrEmpty()) return@observe
            bookmarkAdapter.updateItems(it)
        }
    }

    companion object {
        const val GRID_COLUMN_AMOUNT = 3
    }


}