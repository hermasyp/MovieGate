package com.catnip.moviegate.ui.main.favorites.favoritelist

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.catnip.moviegate.R
import com.catnip.moviegate.data.local.entity.ContentType

private val TAB = listOf(

    Pair(R.string.title_tab_movies,ContentType.MOVIE),
    Pair(R.string.title_tab_tvshows, ContentType.TVSHOWS)
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context?, fm: FragmentManager?) :
    FragmentPagerAdapter(fm!!,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return FavoriteListFragment.newInstance(TAB[position].second.toString())
    }
    override fun getPageTitle(position: Int): CharSequence? {
        return context?.resources?.getString(TAB[position].first)
    }
    override fun getCount(): Int {
        // Show 2 total pages.
        return 2
    }
}