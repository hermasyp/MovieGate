package com.catnip.moviegate.ui.main.favorites.favoritelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.catnip.moviegate.R
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * A placeholder fragment containing a simple view.
 */
class FavoriteListFragment : Fragment() {
    private val favoriteListViewModel: FavoriteListViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tab_favorite, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val type = arguments?.getString(ARG_TYPE)
        type?.let {
            favoriteListViewModel.getFavorite(it)
        }
        favoriteListViewModel.favoriteResult.observe(this, Observer {
        })
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_TYPE = "TYPE"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(type: String): FavoriteListFragment {
            return FavoriteListFragment()
                .apply {
                    arguments = Bundle().apply {
                        putString(ARG_TYPE, type)
                    }
                }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}