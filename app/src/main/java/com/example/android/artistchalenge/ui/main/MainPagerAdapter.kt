package com.example.android.artistchalenge.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.android.artistchalenge.ui.main.bookmark.BookmarkFragment
import com.example.android.artistchalenge.ui.main.search.SearchFragment

class MainPagerAdapter(
    activity: FragmentActivity
) : FragmentStateAdapter(activity) {

    override fun getItemCount() = NUM_PAGES

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SearchFragment()
            1 -> BookmarkFragment()
            else -> throw UnsupportedOperationException()
        }
    }

    companion object {
        private const val NUM_PAGES = 2
    }
}