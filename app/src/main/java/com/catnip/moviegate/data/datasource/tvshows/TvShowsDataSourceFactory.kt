package com.catnip.moviegate.data.datasource.tvshows

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.catnip.moviegate.model.content.Content
import com.catnip.moviegate.data.network.AppScheduler
import com.catnip.moviegate.data.network.RetrofitApi
import io.reactivex.disposables.CompositeDisposable

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/

class TvShowsDataSourceFactory (
    private val api: RetrofitApi,
    private val scheduler: AppScheduler,
    private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, Content>(){
    val tvshowLiveDataSource = MutableLiveData<TvShowsDataSource>()

    override fun create(): DataSource<Int, Content> {
        val tds = TvShowsDataSource(api,scheduler,compositeDisposable)
        tvshowLiveDataSource.postValue(tds)
        return tds
    }

}