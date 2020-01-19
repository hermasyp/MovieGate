package com.catnip.moviegate.ui.main.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.catnip.moviegate.R
import com.catnip.moviegate.ui.main.favorites.favoritelist.FavoriteListViewModel
import com.catnip.moviegate.ui.main.favorites.favoritelist.SectionsPagerAdapter
import kotlinx.android.synthetic.main.fragment_favorite.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : Fragment() {



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view_pager.adapter =
            SectionsPagerAdapter(
                context,
                fragmentManager
            )
        tab_favorites.setupWithViewPager(view_pager)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }
}