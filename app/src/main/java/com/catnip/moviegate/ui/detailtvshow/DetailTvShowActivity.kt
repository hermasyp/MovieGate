package com.catnip.moviegate.ui.detailtvshow

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log.d
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import com.catnip.moviegate.R
import com.catnip.moviegate.di.ScopeNames
import com.catnip.moviegate.model.tvshows.TvShow
import com.catnip.moviegate.network.ResultState
import kotlinx.android.synthetic.main.activity_detail_tv_show.*
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class DetailTvShowActivity : AppCompatActivity() {
    companion object {
        const val ARG_TVSHOW_PARCELABLE = "TVSHOW"
        fun run(context: Context?, tvShow: TvShow?) {
            val intent = Intent(context, DetailTvShowActivity::class.java)
            intent.putExtra(ARG_TVSHOW_PARCELABLE, tvShow)
            context?.startActivity(intent)
        }
    }

    private val scopeId = "detailTvShowsScope"
    private val scopeName = getKoin().createScope(scopeId, named(ScopeNames.DetailTvShowScopes))
    private val detailTvShowViewModel: DetailTvShowViewModel by scopeName.viewModel(this)
    private lateinit var tvShow: TvShow


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_tv_show)
        setSupportActionBar(toolbar)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        getIntentData()
        detailTvShowViewModel.loadDetailMovie(tvShow.id)
        detailTvShowViewModel.detailTvShows.observe(this) {
            when (it) {
                is ResultState.Progress -> {
                    d(DetailTvShowActivity::class.java.simpleName, "On progress")
                }
                is ResultState.Success -> {
                    d(DetailTvShowActivity::class.java.simpleName, it.data.originalName)
                }
                is ResultState.Failure -> {
                    d(DetailTvShowActivity::class.java.simpleName, "Error")
                }
            }
        }
    }
    private fun getIntentData() {
        tvShow = intent.getParcelableExtra(ARG_TVSHOW_PARCELABLE)
    }
    override fun onDestroy() {
        super.onDestroy()
        scopeName.close()
    }
}
