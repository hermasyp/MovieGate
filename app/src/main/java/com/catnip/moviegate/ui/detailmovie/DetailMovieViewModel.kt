package com.catnip.moviegate.ui.detailmovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.catnip.moviegate.model.detailmovie.DetailMovie
import com.catnip.moviegate.data.network.ResultState

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class DetailMovieViewModel(private val movieRepository: DetailMovieRepository) : ViewModel() {

    val detailMovie : LiveData<ResultState<DetailMovie>> by lazy {
        movieRepository.detailMovieResult
    }

    fun loadDetailMovie(idMovie : String){
        movieRepository.fetchDetail(idMovie)
    }

    override fun onCleared() {
        super.onCleared()
        movieRepository.onCleared()
    }
}