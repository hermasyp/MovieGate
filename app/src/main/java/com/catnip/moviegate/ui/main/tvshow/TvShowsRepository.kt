package com.catnip.moviegate.ui.main.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.catnip.moviegate.base.Constants
import com.catnip.moviegate.datasource.tvshows.TvShowsDataSource
import com.catnip.moviegate.datasource.tvshows.TvShowsDataSourceFactory
import com.catnip.moviegate.model.content.Content
import com.catnip.moviegate.network.PaginateResultState

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class TvShowsRepository(private val factory: TvShowsDataSourceFactory){
    private lateinit var tvshowsPageList : LiveData<PagedList<Content>>

    fun fetchTvShows() : LiveData<PagedList<Content>>{
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(Constants.ITEM_PER_PAGE)
            .build()
        tvshowsPageList = LivePagedListBuilder(factory,config).build()
        return tvshowsPageList
    }
    fun getFetchState() : LiveData<PaginateResultState>{
        return Transformations.switchMap<TvShowsDataSource,PaginateResultState>(
            factory.tvshowLiveDataSource,
            TvShowsDataSource::state
        )
    }
}