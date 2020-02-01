package com.catnip.moviegate.ui.main.favorites.favoritelist

import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.catnip.moviegate.R
import com.catnip.moviegate.data.local.entity.ContentType
import com.catnip.moviegate.data.network.ResultState
import com.catnip.moviegate.ui.detailmovie.DetailMovieActivity
import com.catnip.moviegate.ui.detailtvshow.DetailTvShowActivity
import com.catnip.moviegate.ui.widget.WidgetTools
import kotlinx.android.synthetic.main.fragment_tab_favorite.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A placeholder fragment containing a simple view.
 */
class FavoriteListFragment : Fragment() {
    private val favoriteListViewModel: FavoriteListViewModel by viewModel()
    private val TAG = FavoriteListFragment::class.java.simpleName
    private lateinit var favoriteListAdapter: FavoriteListAdapter
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
        rv_favorites.layoutManager = LinearLayoutManager(context)
        rv_favorites.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        favoriteListAdapter = FavoriteListAdapter(
            {
                if (it?.type == ContentType.MOVIE.toString()) {
                    DetailMovieActivity.run(context, it.id)
                } else {
                    DetailTvShowActivity.run(context, it?.id)
                }
            },
            {
                it?.let { fav ->

                    context?.let { it1 -> WidgetTools.changeWidget(it1) }
                    favoriteListViewModel.deleteFavorite(fav) }
            })

        favoriteListViewModel.deleteResult.observe(this) {
            when (it) {
                is ResultState.Progress -> {
                    d(TAG, "On progress")
                }
                is ResultState.Success -> {
                    type?.let { it1 -> favoriteListViewModel.getFavorite(it1) }
                }
                is ResultState.Failure -> {
                    d(TAG, "Error")
                }
            }
        }

        favoriteListViewModel.favoriteResult.observe(this) {
            when (it) {
                is ResultState.Progress -> {
                    d(TAG, "On progress")
                }
                is ResultState.Success -> {
                    d(TAG, type + " " + it.data.size.toString())
                    favoriteListAdapter.items = it.data
                    rv_favorites.adapter = favoriteListAdapter
                }
                is ResultState.Failure -> {
                    d(TAG, "Error")
                }
            }
        }
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

}