package com.catnip.moviegate.ui.detailmovie

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log.d
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import coil.api.load
import com.catnip.moviegate.BuildConfig
import com.catnip.moviegate.R
import com.catnip.moviegate.di.ScopeNames
import com.catnip.moviegate.model.detailmovie.DetailMovie
import com.catnip.moviegate.model.movies.Movie
import com.catnip.moviegate.network.ResultState
import com.catnip.moviegate.utils.genre.GenreGenerator
import kotlinx.android.synthetic.main.activity_detail_movie.*
import kotlinx.android.synthetic.main.content_detail_movie.*
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
    private lateinit var detailMovie: DetailMovie
    private val TAG = DetailMovieActivity::class.java.simpleName


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)
        setSupportActionBar(toolbar)
        toolbar_layout.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        getIntentData()
        detailMovieViewModel.loadDetailMovie(movie.id)
        detailMovieViewModel.detailMovie.observe(this) {
            when (it) {
                is ResultState.Progress -> {
                    d(TAG, "On progress")
                }
                is ResultState.Success -> {
                    d(TAG ,it.data.title)
                    this.detailMovie = it.data
                    setDetailData()
                }
                is ResultState.Failure -> {
                    d(TAG, "Error")
                }
            }

        }
    }

    private fun getIntentData() {
        movie = intent.getParcelableExtra(ARG_MOVIE_PARCELABLE)
    }

    private fun setDetailData(){
        toolbar_layout.title = detailMovie.title
        supportActionBar?.subtitle = detailMovie.tagline
        txt_title_detail.text = detailMovie.title
        txt_detail_content.text = detailMovie.overview
        txt_genre_content.text = GenreGenerator.getAllGenre(detailMovie.genres)
        rating_view.rating = (detailMovie.voteAverage.div(2)).toFloat()
        txt_rating_total.text = detailMovie.voteAverage.toString()
        img_banner_movie.load(BuildConfig.BASE_POSTER_IMG_URL + detailMovie.backdropPath)
        img_poster_detail.load(BuildConfig.BASE_POSTER_IMG_URL + detailMovie.posterPath)
    }
    override fun onDestroy() {
        super.onDestroy()
        scopeName.close()
    }
}
