package com.catnip.moviegate.datasource.detailmovies

import com.catnip.moviegate.ext.*
import com.catnip.moviegate.model.detailtvshow.DetailTvShows
import com.catnip.moviegate.network.AppScheduler
import com.catnip.moviegate.network.ResultState
import com.catnip.moviegate.network.RetrofitApi
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class DetailTVShowDataSource (
    private val api: RetrofitApi,
    private val scheduler: AppScheduler,
    private val compositeDisposable: CompositeDisposable) {

    val detailResult : PublishSubject<ResultState<DetailTvShows>> =
        PublishSubject.create<ResultState<DetailTvShows>>()

    fun fetchDetailTvShow(idTvShows : String){
        detailResult.loading(true)
        api.getDetailTvShow(idTvShows)
            .performOnBackOutOnMain(scheduler)
            .subscribe(
                {
                    detailResult.success(it)
                },
                {
                    detailResult.failed(it)
                }
            ).addTo(compositeDisposable)
    }
}