package com.catnip.moviegate.ui.detailtvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.catnip.moviegate.model.detailtvshow.DetailTvShows
import com.catnip.moviegate.data.network.ResultState

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class DetailTvShowViewModel(private val tvShowRepository: DetailTvShowRepository) : ViewModel() {

    val detailTvShows : LiveData<ResultState<DetailTvShows>> by lazy {
        tvShowRepository.detailMovieResult
    }

    fun loadDetailMovie(idTvShows : String){
        tvShowRepository.fetchDetail(idTvShows)
    }

    override fun onCleared() {
        super.onCleared()
        tvShowRepository.onCleared()
    }
}