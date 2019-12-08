package com.catnip.moviegate.ui.detailmovie

import androidx.lifecycle.LiveData
import com.catnip.moviegate.datasource.detailmovies.DetailMovieDataSource
import com.catnip.moviegate.ext.toLiveData
import com.catnip.moviegate.model.detailmovie.DetailMovie
import com.catnip.moviegate.network.ResultState
import io.reactivex.disposables.CompositeDisposable

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class DetailMovieRepository(private val dataSource : DetailMovieDataSource, private val compositeDisposable: CompositeDisposable) {

    val detailMovieResult: LiveData<ResultState<DetailMovie>> by lazy {
        dataSource.detailResult.toLiveData(compositeDisposable)
    }

    fun fetchDetail(idMovie : String) {
        dataSource.fetchDetailMovie(idMovie)
    }

    fun onCleared(){
        compositeDisposable.clear()
    }
}