package com.example.android.artistchalenge.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.android.artistchalenge.ui.main.search.SearchFragment
import java.lang.UnsupportedOperationException

class MainPagerAdapter(activity: FragmentActivity, private val listener: MainActivityNavigationListener): FragmentStateAdapter(activity) {
    override fun getItemCount() = NUM_PAGES
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0, 1 -> SearchFragment().apply {
                this.mainActivityNavigationListener = listener
            }
            else -> throw UnsupportedOperationException()
        }
    }

    companion object {
        private const val NUM_PAGES = 2
    }
}