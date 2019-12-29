package com.catnip.moviegate.ui.main.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.catnip.moviegate.base.Constants
import com.catnip.moviegate.data.datasource.movies.MoviesDataSource
import com.catnip.moviegate.data.datasource.movies.MoviesDataSourceFactory
import com.catnip.moviegate.model.content.Content
import com.catnip.moviegate.data.network.PaginateResultState

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/

class MoviesRepository(private val factory: MoviesDataSourceFactory) {
    lateinit var contentsPagedList: LiveData<PagedList<Content>>

    fun fetchMovies() : LiveData<PagedList<Content>>{
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(Constants.ITEM_PER_PAGE)
            .build()
        contentsPagedList = LivePagedListBuilder(factory,config).build()
        return contentsPagedList
    }

    fun getFetchState(): LiveData<PaginateResultState>{
        return Transformations.switchMap<MoviesDataSource,PaginateResultState>(
            factory.moviesLiveDataSource,
            MoviesDataSource::state
        )
    }
}