package com.catnip.moviegate.ui.detailtvshow

import androidx.lifecycle.LiveData
import com.catnip.moviegate.data.datasource.detailmovies.DetailTVShowDataSource
import com.catnip.moviegate.ext.toLiveData
import com.catnip.moviegate.model.detailtvshow.DetailTvShows
import com.catnip.moviegate.data.network.ResultState
import io.reactivex.disposables.CompositeDisposable

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class DetailTvShowRepository(private val dataSource : DetailTVShowDataSource, private val compositeDisposable: CompositeDisposable) {

    val detailMovieResult: LiveData<ResultState<DetailTvShows>> by lazy {
        dataSource.detailResult.toLiveData(compositeDisposable)
    }

    fun fetchDetail(idTvShow : String) {
        dataSource.fetchDetailTvShow(idTvShow)
    }

    fun onCleared(){
        compositeDisposable.clear()
    }
}