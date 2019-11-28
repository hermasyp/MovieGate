package com.catnip.moviegate.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.catnip.moviegate.base.Constants.Companion.FIRST_PAGE
import com.catnip.moviegate.ext.addTo
import com.catnip.moviegate.ext.performOnBackOutOnMain
import com.catnip.moviegate.model.movies.Movie
import com.catnip.moviegate.network.*
import io.reactivex.disposables.CompositeDisposable

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class MoviesDataSource(
    private val api: RetrofitApi,
    private val scheduler: Scheduler,
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, Movie>() {

    val state : MutableLiveData<PaginateResultState> = MutableLiveData()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        state.postValue(PaginateResultState.LOADING)
        api.getMovies(FIRST_PAGE)
            .performOnBackOutOnMain(scheduler)
            .subscribe(
                {
                    state.postValue(PaginateResultState.LOADED)
                    callback.onResult(it.movies, null, FIRST_PAGE + 1)
                },
                {
                    state.postValue(PaginateResultState.ERROR)
                })
            .addTo(compositeDisposable)

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        state.postValue(PaginateResultState.LOADING)
        api.getMovies(params.key)
            .performOnBackOutOnMain(scheduler)
            .subscribe(
                {
                    if(it.totalPages >= params.key) {
                        callback.onResult(it.movies, params.key+1)
                        state.postValue(PaginateResultState.LOADED)
                    }
                    else{
                        state.postValue(PaginateResultState.EOF)
                    }
                },
                {
                    state.postValue(PaginateResultState.ERROR)
                }
            ).addTo(compositeDisposable)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}