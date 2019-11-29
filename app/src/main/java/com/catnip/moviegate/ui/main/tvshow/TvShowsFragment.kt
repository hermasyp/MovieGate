package com.catnip.moviegate.ui.main.tvshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.catnip.moviegate.R
import com.catnip.moviegate.di.ScopeNames
import com.catnip.moviegate.utils.recyclerview.GridSpacingItemDecoration
import kotlinx.android.synthetic.main.fragment_tvshows.*
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class TvShowsFragment : Fragment() {

    private val scopeId = "tvshowsListScope"
    private val scopeName = getKoin().createScope(scopeId, named(ScopeNames.TvShowsListScopes))
    private val tvShowsViewModel: TvShowsViewModel by scopeName.viewModel(this)
    private lateinit var tvShowAdapter: TvShowAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tvshows, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvShowAdapter = TvShowAdapter()
        val gridLayoutManager = GridLayoutManager(context, 3)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = tvShowAdapter.getItemViewType(position)
                return if (viewType == tvShowAdapter.TVSHOW_VIEW_TYPE) 1 else 3
            }
        }
        rv_tvshows.addItemDecoration(GridSpacingItemDecoration(3, 20, true))
        rv_tvshows.layoutManager = gridLayoutManager
        rv_tvshows.setHasFixedSize(true)
        rv_tvshows.adapter = tvShowAdapter
        tvShowsViewModel.tvshows.observe(this, Observer {
            tvShowAdapter.submitList(it)
        })
        tvShowsViewModel.resultState.observe(this, Observer {
            tvShowAdapter.setState(it)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        scopeName.close()
    }
}