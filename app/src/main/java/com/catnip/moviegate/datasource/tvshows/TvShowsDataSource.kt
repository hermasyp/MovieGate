package com.catnip.moviegate.datasource.tvshows

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.catnip.moviegate.base.Constants.Companion.FIRST_PAGE
import com.catnip.moviegate.ext.addTo
import com.catnip.moviegate.ext.performOnBackOutOnMain
import com.catnip.moviegate.model.tvshows.TvShow
import com.catnip.moviegate.network.PaginateResultState
import com.catnip.moviegate.network.RetrofitApi
import com.catnip.moviegate.network.Scheduler
import io.reactivex.disposables.CompositeDisposable

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/

class TvShowsDataSource(
    private val api: RetrofitApi,
    private val scheduler: Scheduler,
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, TvShow>() {

    val state: MutableLiveData<PaginateResultState> = MutableLiveData()


    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, TvShow>
    ) {
        state.postValue(PaginateResultState.LOADING)
        api.getTvShows(FIRST_PAGE)
            .performOnBackOutOnMain(scheduler)
            .subscribe(
                {
                    state.postValue(PaginateResultState.LOADED)
                    callback.onResult(it.results, null, FIRST_PAGE + 1)
                },
                {
                    state.postValue(PaginateResultState.ERROR)
                }
            ).addTo(compositeDisposable)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, TvShow>) {
        state.postValue(PaginateResultState.LOADING)
        api.getTvShows(params.key)
            .performOnBackOutOnMain(scheduler)
            .subscribe(
                {
                    if (it.totalPages >= params.key) {
                        callback.onResult(it.results, params.key + 1)
                        state.postValue(PaginateResultState.LOADED)
                    } else {
                        state.postValue(PaginateResultState.EOF)
                    }
                },
                {
                    state.postValue(PaginateResultState.ERROR)
                }
            ).addTo(compositeDisposable)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, TvShow>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}