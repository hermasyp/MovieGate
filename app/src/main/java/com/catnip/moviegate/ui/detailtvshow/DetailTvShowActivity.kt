package com.catnip.moviegate.ui.detailtvshow

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
import com.catnip.moviegate.model.detailtvshow.DetailTvShows
import com.catnip.moviegate.data.network.ResultState
import com.catnip.moviegate.utils.genre.GenreGenerator
import kotlinx.android.synthetic.main.activity_detail_tv_show.*
import kotlinx.android.synthetic.main.content_detail_movie.*
import kotlinx.android.synthetic.main.content_detail_tv_show.*
import kotlinx.android.synthetic.main.content_detail_tv_show.img_favorite
import kotlinx.android.synthetic.main.content_detail_tv_show.img_poster_detail
import kotlinx.android.synthetic.main.content_detail_tv_show.rating_view
import kotlinx.android.synthetic.main.content_detail_tv_show.txt_detail_content
import kotlinx.android.synthetic.main.content_detail_tv_show.txt_genre_content
import kotlinx.android.synthetic.main.content_detail_tv_show.txt_rating_total
import kotlinx.android.synthetic.main.content_detail_tv_show.txt_title_detail
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class DetailTvShowActivity : AppCompatActivity() {
    companion object {
        const val ARG_TVSHOW_PARCELABLE = "TVSHOW"
        fun run(context: Context?, tvShow: String?) {
            val intent = Intent(context, DetailTvShowActivity::class.java)
            intent.putExtra(ARG_TVSHOW_PARCELABLE, tvShow)
            context?.startActivity(intent)
        }
    }

    private val scopeId = "detailTvShowsScope"
    private val scopeName = getKoin().createScope(scopeId, named(ScopeNames.DetailTvShowScopes))
    private val detailTvShowViewModel: DetailTvShowViewModel by scopeName.viewModel(this)
    private lateinit var tvShow: String
    private lateinit var detailTvShow: DetailTvShows
    private val TAG = DetailTvShowActivity::class.java.simpleName


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_tv_show)
        setSupportActionBar(toolbar)
        toolbar_layout.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        fetchData()
    }

    private fun setupFavoriteAction() {
        img_favorite.visibility = View.VISIBLE
        detailTvShowViewModel.isFavorited(detailTvShow.id)
        detailTvShowViewModel.isFavorited.observe(this) {
            when (it) {
                is ResultState.Progress -> {
                    d(TAG, "On progress")
                }
                is ResultState.Success -> {
                    if (it.data) {
                        img_favorite.setImageResource(R.drawable.ic_true_favorite)
                        img_favorite.setOnClickListener {
                            detailTvShowViewModel.deleteFromFavorite(detailTvShow)
                            detailTvShowViewModel.isFavorited(detailTvShow.id)
                        }
                    } else {
                        img_favorite.setImageResource(R.drawable.ic_false_favorites)
                        img_favorite.setOnClickListener {
                            detailTvShowViewModel.setToFavorite(detailTvShow)
                            detailTvShowViewModel.isFavorited(detailTvShow.id)
                        }
                    }
                }
                is ResultState.Failure -> {
                    d(TAG, "Error")
                }
            }
        }
    }

    private fun fetchData(){
        getIntentData()
        detailTvShowViewModel.loadDetailMovie(tvShow)
        detailTvShowViewModel.detailTvShows.observe(this) {
            when (it) {
                is ResultState.Progress -> {
                    d(TAG, "On progress")
                }
                is ResultState.Success -> {
                    d(TAG, it.data.originalName)
                    this.detailTvShow = it.data
                    setDetailData()
                    setupFavoriteAction()
                }
                is ResultState.Failure -> {
                    d(TAG, "Error")
                }
            }
        }
        detailTvShowViewModel.deleteFavorite.observe(this) {
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

        detailTvShowViewModel.setToFavorite.observe(this) {
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
    private fun setDetailData() {
        toolbar_layout.title = detailTvShow.name
        supportActionBar?.subtitle = detailTvShow.status
        txt_title_detail.text = detailTvShow.name
        txt_detail_content.text = detailTvShow.overview
        txt_genre_content.text = GenreGenerator.getAllGenre(detailTvShow.genres)
        rating_view.rating = (detailTvShow.voteAverage.div(2)).toFloat()
        txt_rating_total.text = detailTvShow.voteAverage.toString()
        img_banner_movie.load(BuildConfig.BASE_POSTER_IMG_URL + detailTvShow.backdropPath)
        img_poster_detail.load(BuildConfig.BASE_POSTER_IMG_URL + detailTvShow.posterPath)    }

    private fun getIntentData() {
        tvShow = intent.getStringExtra(ARG_TVSHOW_PARCELABLE)
    }
    override fun onDestroy() {
        super.onDestroy()
        scopeName.close()
    }
}
