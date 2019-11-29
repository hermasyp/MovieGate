package com.catnip.moviegate.ui.main.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.catnip.moviegate.base.Constants
import com.catnip.moviegate.datasource.movies.MoviesDataSource
import com.catnip.moviegate.datasource.movies.MoviesDataSourceFactory
import com.catnip.moviegate.model.movies.Movie
import com.catnip.moviegate.network.PaginateResultState

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/

class MoviesRepository(private val factory: MoviesDataSourceFactory) {
    lateinit var moviesPagedList: LiveData<PagedList<Movie>>

    fun fetchMovies() : LiveData<PagedList<Movie>>{
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(Constants.ITEM_PER_PAGE)
            .build()
        moviesPagedList = LivePagedListBuilder(factory,config).build()
        return moviesPagedList
    }

    fun getFetchState(): LiveData<PaginateResultState>{
        return Transformations.switchMap<MoviesDataSource,PaginateResultState>(
            factory.moviesLiveDataSource,
            MoviesDataSource::state
        )
    }
}