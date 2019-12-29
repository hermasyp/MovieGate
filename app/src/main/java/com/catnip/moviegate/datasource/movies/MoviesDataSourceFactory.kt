package com.catnip.moviegate.datasource.movies

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.catnip.moviegate.model.content.Content
import com.catnip.moviegate.network.AppScheduler
import com.catnip.moviegate.network.RetrofitApi
import io.reactivex.disposables.CompositeDisposable


/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class MoviesDataSourceFactory(
    private val api: RetrofitApi,
    private val scheduler: AppScheduler,
    private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, Content>() {

    val moviesLiveDataSource = MutableLiveData<MoviesDataSource>()
    override fun create(): DataSource<Int, Content> {
        val mds = MoviesDataSource(
            api,
            scheduler,
            compositeDisposable
        )
        moviesLiveDataSource.postValue(mds)
        return mds
    }

}