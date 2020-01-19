package com.catnip.moviegate.ui.detailmovie

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log.d
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import coil.api.load
import com.catnip.moviegate.BuildConfig
import com.catnip.moviegate.R
import com.catnip.moviegate.di.ScopeNames
import com.catnip.moviegate.model.content.Content
import com.catnip.moviegate.model.detailmovie.DetailMovie
import com.catnip.moviegate.data.network.ResultState
import com.catnip.moviegate.utils.genre.GenreGenerator
import kotlinx.android.synthetic.main.activity_detail_movie.*
import kotlinx.android.synthetic.main.content_detail_movie.*
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class DetailMovieActivity : AppCompatActivity() {
    companion object {
        const val ARG_MOVIE_PARCELABLE = "MOVIE"
        fun run(context: Context?, movie: String?) {
            val intent = Intent(context, DetailMovieActivity::class.java)
            intent.putExtra(ARG_MOVIE_PARCELABLE, movie)
            context?.startActivity(intent)
        }
    }

    private val scopeId = "detailMovieScope"
    private val scopeName = getKoin().createScope(scopeId, named(ScopeNames.DetailMovieScopes))
    private val detailMovieViewModel: DetailMovieViewModel by scopeName.viewModel(this)
    private lateinit var movie: String
    private lateinit var detailMovie: DetailMovie
    private val TAG = DetailMovieActivity::class.java.simpleName


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)
        setSupportActionBar(toolbar)
        toolbar_layout.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        fetchData()
    }

    private fun setupFavoriteAction() {
        img_favorite.visibility = View.VISIBLE
        detailMovieViewModel.isFavorited(movie)
        detailMovieViewModel.isFavorited.observe(this) {
            when (it) {
                is ResultState.Progress -> {
                    d(TAG, "On progress")
                }
                is ResultState.Success -> {
                    if (it.data) {
                        img_favorite.setImageResource(R.drawable.ic_true_favorite)
                        img_favorite.setOnClickListener {
                            detailMovieViewModel.deleteFromFavorite(detailMovie)
                            detailMovieViewModel.isFavorited(movie)
                        }
                    } else {
                        img_favorite.setImageResource(R.drawable.ic_false_favorites)
                        img_favorite.setOnClickListener {
                            detailMovieViewModel.setToFavorite(detailMovie)
                            detailMovieViewModel.isFavorited(movie)
                        }
                    }
                }
                is ResultState.Failure -> {
                    d(TAG, "Error")
                }
            }
        }
    }

    private fun fetchData() {
        getIntentData()
        detailMovieViewModel.loadDetailMovie(movie)
        detailMovieViewModel.detailMovie.observe(this) {
            when (it) {
                is ResultState.Progress -> {
                    d(TAG, "On progress")
                }
                is ResultState.Success -> {
                    d(TAG, it.data.title)
                    this.detailMovie = it.data
                    setDetailData()
                    setupFavoriteAction()
                }
                is ResultState.Failure -> {
                    d(TAG, "Error")
                }
            }

        }
        detailMovieViewModel.deleteFavorite.observe(this) {
            when (it) {
                is ResultState.Progress -> {
                    d(TAG, "On progress")
                }
                is ResultState.Success -> {
                    if (it.data) {
                        Toast.makeText(this, "Delete Favorite Success", Toast.LENGTH_SHORT).show()
                    }
                }
                is ResultState.Failure -> {
                    d(TAG, "Error")
                }
            }
        }

        detailMovieViewModel.setToFavorite.observe(this) {
            when (it) {
                is ResultState.Progress -> {
                    d(TAG, "On progress")
                }
                is ResultState.Success -> {
                    if (it.data) {
                        Toast.makeText(this, "Set Favorite Success", Toast.LENGTH_SHORT).show()
                    }
                }
                is ResultState.Failure -> {
                    d(TAG, "Error")
                }
            }
        }
    }

    private fun getIntentData() {
        movie = intent.getStringExtra(ARG_MOVIE_PARCELABLE)
    }

    private fun setDetailData() {
        toolbar_layout.title = detailMovie.originalTitle
        supportActionBar?.subtitle = detailMovie.tagline
        txt_title_detail.text = detailMovie.originalTitle
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
