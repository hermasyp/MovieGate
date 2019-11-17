package com.catnip.moviegate.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.catnip.moviegate.model.movies.Movie
import com.catnip.moviegate.network.RetrofitApi
import com.catnip.moviegate.network.Scheduler
import io.reactivex.disposables.CompositeDisposable


/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class MoviesDataSourceFactory(
    private val api: RetrofitApi,
    private val scheduler: Scheduler,
    private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, Movie>() {

    val moviesLiveDataSource = MutableLiveData<MoviesDataSource>()
    override fun create(): DataSource<Int, Movie> {
        val mds = MoviesDataSource(api, scheduler, compositeDisposable)
        moviesLiveDataSource.postValue(mds)
        return mds
    }

}