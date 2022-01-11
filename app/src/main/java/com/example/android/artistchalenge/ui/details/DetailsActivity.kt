package com.example.android.artistchalenge.ui.details

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.example.android.artistchalenge.R
import com.example.android.artistchalenge.databinding.ActivityDetailsBinding
import com.example.android.artistchalenge.di.ViewModelFactory
import com.example.android.artistchalenge.ui.ArtistChallengeApplication
import com.example.android.artistchalenge.ui.showAndSetOnNotNull
import javax.inject.Inject

class DetailsActivity : AppCompatActivity() {
    private var _binding: ActivityDetailsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by viewModels<DetailsViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        (application as ArtistChallengeApplication).getComponent().inject(this)

        viewModel.loadArtistDetails(intent.getStringExtra(DETAILS_ACTIVITY_ARTIST_ID_EXTRA)!!)

        viewModel.artistUIModel.observe(this) {
            Glide.with(this).load(it.image).centerCrop()
                .placeholder(R.drawable.ic_actor_placeholder).into(binding.artistImage)
            binding.name.showAndSetOnNotNull(it.name)
            binding.description.showAndSetOnNotNull(it.description)
            binding.biography.showAndSetOnNotNull(it.biography)
            it.biography?.let { biography ->
                binding.biography.isVisible = true
                binding.biography.setText(
                    HtmlCompat.fromHtml(biography, HtmlCompat.FROM_HTML_MODE_COMPACT))
            }
            binding.groupMembers.showAndSetOnNotNull(it.groupMembers)
            binding.info.showAndSetOnNotNull(it.info)

            bindBookmarkButton(it.isBookmarked)
        }

        viewModel.screenState.observe(this) {
            viewModel.screenState.observe(this) {
                when (it) {
                    States.LOADING -> {
                        binding.progressBar.isInvisible = false
                    }
                    States.DEFAULT -> {
                        binding.progressBar.isInvisible = true
                    }
                    States.ERROR -> {
                        binding.progressBar.isInvisible = true
                    }
                    States.NO_ARTISTS -> {
                        binding.progressBar.isInvisible = true
                    }
                }
            }
        }
    }

    private fun bindBookmarkButton(isBookmarked: Boolean) {
        val bookmarkImage = if (isBookmarked) R.drawable.ic_bookmark_selected else R.drawable.ic_bookmark_unselected
        binding.bookmarkButton.setImageResource(bookmarkImage)
        binding.bookmarkButton.isVisible = true
        binding.bookmarkButton.setOnClickListener {
            viewModel.onBookmarkClicked()
        }
    }

    enum class States {
        DEFAULT, LOADING, ERROR, NO_ARTISTS
    }

    companion object {
        const val DETAILS_ACTIVITY_ARTIST_ID_EXTRA = "DETAILS_ACTIVITY_ARTIST_ID_EXTRA"
    }
}