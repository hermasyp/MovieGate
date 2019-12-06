package com.catnip.moviegate.ui.detailmovie

import androidx.lifecycle.LiveData
import com.catnip.moviegate.datasource.detailmovies.DetailMovieDataSource
import com.catnip.moviegate.model.detailmovie.DetailMovie
import com.catnip.moviegate.network.ResultState

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class DetailMovieRepository(private val dataSource : DetailMovieDataSource) {
    fun fetchDetail(idMovie : String) : LiveData<ResultState<DetailMovie>>{
        dataSource.fetchDetailMovie(idMovie)
        return dataSource.detailMoviesResult
    }
}