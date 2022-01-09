package com.example.android.artistchalenge.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.android.artistchalenge.R
import com.example.android.artistchalenge.databinding.ActivityMainBinding
import com.example.android.artistchalenge.ui.details.DetailsActivity
import com.example.android.artistchalenge.ui.main.search.SearchFragment

class MainActivity : AppCompatActivity(), MainActivityNavigationListener {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val searchFragment = SearchFragment()
        searchFragment.mainActivityNavigationListener = this
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, searchFragment)
            .commit()
    }

    override fun onActorDetailedClicked(id: String) {
        val detailsActivityIntent = Intent(this, DetailsActivity::class.java)
        detailsActivityIntent.putExtra(DetailsActivity.DETAILS_ACTIVITY_ARTIST_ID_EXTRA, id)
        startActivity(detailsActivityIntent)
    }
}

interface MainActivityNavigationListener {
    fun onActorDetailedClicked(id: String)
}