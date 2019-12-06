package com.catnip.moviegate.datasource.detailmovies

import androidx.lifecycle.MutableLiveData
import com.catnip.moviegate.ext.addTo
import com.catnip.moviegate.ext.performOnBackOutOnMain
import com.catnip.moviegate.model.detailmovie.DetailMovie
import com.catnip.moviegate.network.ResultState
import com.catnip.moviegate.network.RetrofitApi
import com.catnip.moviegate.network.Scheduler
import io.reactivex.disposables.CompositeDisposable

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class DetailMovieDataSource (
    private val api: RetrofitApi,
    private val scheduler: Scheduler,
    private val compositeDisposable: CompositeDisposable) {

    val detailMoviesResult = MutableLiveData<ResultState<DetailMovie>>()

    fun fetchDetailMovie(idMovie : String){
        detailMoviesResult.postValue(ResultState.loading(true))
        api.getDetailMovie(idMovie)
            .performOnBackOutOnMain(scheduler)
            .subscribe(
                {
                    detailMoviesResult.postValue(ResultState.success(it))
                },
                {
                    detailMoviesResult.postValue(ResultState.failure(it))
                }
            ).addTo(compositeDisposable)
    }
}