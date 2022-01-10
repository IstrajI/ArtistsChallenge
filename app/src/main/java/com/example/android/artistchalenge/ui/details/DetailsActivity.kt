package com.example.android.artistchalenge.ui.details

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.example.android.artistchalenge.R
import com.example.android.artistchalenge.databinding.ActivityDetailsBinding
import com.example.android.artistchalenge.di.ViewModelFactory
import com.example.android.artistchalenge.ui.ArtistChallengeApplication
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

        viewModel.loadArtistDetails(intent.getStringExtra(DETAILS_ACTIVITY_ARTIST_ID_EXTRA))

        viewModel.artistUIModel.observe(this) {
            Glide.with(this).load(it.image).centerCrop()
                .placeholder(R.drawable.ic_actor_placeholder).into(binding.artistImage)
            it.name?.let { name ->
                binding.name.isVisible = true
                binding.name.text = name
            }
            it.description?.let { description ->
                binding.description.isVisible = true
                binding.description.text = description
            }
            it.biography?.let { biography ->
                binding.biography.isVisible = true
                binding.biography.text =
                    HtmlCompat.fromHtml(biography, HtmlCompat.FROM_HTML_MODE_COMPACT)
            }
            it.groupMembers?.let { groupMembers ->
                binding.groupMembers.isVisible = true
                binding.groupMembers.text = groupMembers
            }
            it.info?.let { info ->
                binding.info.isVisible = true
                binding.info.text = info
            }
            binding.bookmarkButton.setOnClickListener {
                viewModel.onBookmarkClicked()
            }
        }
    }

    companion object {
        const val DETAILS_ACTIVITY_ARTIST_ID_EXTRA = "DETAILS_ACTIVITY_ARTIST_ID_EXTRA"
    }
}