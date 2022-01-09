package com.example.android.artistchalenge.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.android.artistchalenge.R
import com.example.android.artistchalenge.databinding.ActivityMainBinding
import com.example.android.artistchalenge.ui.details.DetailsActivity
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity(), MainActivityNavigationListener {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewPager()
    }

    private fun initViewPager() {
        val tabTitles = arrayOf(
            getString(R.string.mainSearchTabTitle),
            getString(R.string.mainBookMarkedTabTitle)
        )
        binding.viewPager.adapter = MainPagerAdapter(this, this)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabTitles[position]
            binding.viewPager.currentItem = tab.position
        }.attach()
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