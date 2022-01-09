package com.example.android.artistchalenge.ui.details

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
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

        viewModel.artist.observe(this) {
            Glide.with(this).load(it.image).centerInside()
                .placeholder(R.drawable.ic_search_placeholder).into(binding.artistImage)
            binding.name.text = it.name
            binding.type.text = it.type
            binding.groupMembers.text = it.groupMembers?.joinToString(separator = " ")
            binding.biography.text = it.biography
            binding.listeners.text = it.listeners.toString()
            binding.country.text = it.country
            binding.lifeStart.text = it.lifeStart
            binding.lifeEnd.text = it.lifeEnd
        }
    }

    companion object {
        const val DETAILS_ACTIVITY_ARTIST_ID_EXTRA = "DETAILS_ACTIVITY_ARTIST_ID_EXTRA"
    }
}