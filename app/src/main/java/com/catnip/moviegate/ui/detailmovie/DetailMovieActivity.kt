package com.catnip.moviegate.ui.detailmovie

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log.d
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import com.catnip.moviegate.R
import com.catnip.moviegate.di.ScopeNames
import com.catnip.moviegate.model.movies.Movie
import com.catnip.moviegate.network.ResultState
import kotlinx.android.synthetic.main.activity_detail_movie.*
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class DetailMovieActivity : AppCompatActivity() {
    companion object {
        const val ARG_MOVIE_PARCELABLE = "MOVIE"
        fun run(context: Context?, movie: Movie?) {
            val intent = Intent(context, DetailMovieActivity::class.java)
            intent.putExtra(ARG_MOVIE_PARCELABLE, movie)
            context?.startActivity(intent)
        }
    }

    private val scopeId = "detailMovieScope"
    private val scopeName = getKoin().createScope(scopeId, named(ScopeNames.DetailMovieScopes))
    private val detailMovieViewModel: DetailMovieViewModel by scopeName.viewModel(this)
    private lateinit var movie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        getIntentData()
        detailMovieViewModel.loadDetailMovie(movie.id)
        detailMovieViewModel.detailMovie.observe(this) {
            when (it) {
                is ResultState.Progress -> {
                    d(DetailMovieActivity::class.java.simpleName, "On progress")
                }
                is ResultState.Success -> {
                    d(DetailMovieActivity::class.java.simpleName, it.data.title)
                }
                is ResultState.Failure -> {
                    d(DetailMovieActivity::class.java.simpleName, "Error")
                }
            }

        }
    }

    private fun getIntentData() {
        movie = intent.getParcelableExtra(ARG_MOVIE_PARCELABLE)
    }

    override fun onDestroy() {
        super.onDestroy()
        scopeName.close()
    }
}
