package com.catnip.moviegate.data.datasource.movies

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.catnip.moviegate.base.Constants.Companion.FIRST_PAGE
import com.catnip.moviegate.ext.addTo
import com.catnip.moviegate.ext.performOnBackOutOnMain
import com.catnip.moviegate.model.content.Content
import com.catnip.moviegate.data.network.*
import io.reactivex.disposables.CompositeDisposable

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class MoviesDataSource(
    private val api: RetrofitApi,
    private val scheduler: Scheduler,
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, Content>() {

    val state : MutableLiveData<PaginateResultState> = MutableLiveData()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Content>
    ) {
        state.postValue(PaginateResultState.LOADING)
        api.getMovies(FIRST_PAGE)
            .performOnBackOutOnMain(scheduler)
            .subscribe(
                {
                    state.postValue(PaginateResultState.LOADED)
                    callback.onResult(it.datas, null, FIRST_PAGE + 1)
                },
                {
                    state.postValue(PaginateResultState.ERROR)
                })
            .addTo(compositeDisposable)

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Content>) {
        state.postValue(PaginateResultState.LOADING)
        api.getMovies(params.key)
            .performOnBackOutOnMain(scheduler)
            .subscribe(
                {
                    if(it.totalPages >= params.key) {
                        callback.onResult(it.datas, params.key+1)
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

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Content>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}