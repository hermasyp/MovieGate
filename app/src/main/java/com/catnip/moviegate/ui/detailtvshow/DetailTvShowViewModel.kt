package com.catnip.moviegate.ui.detailtvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.catnip.moviegate.model.detailtvshow.DetailTvShows
import com.catnip.moviegate.data.network.ResultState
import com.catnip.moviegate.ui.main.favorites.favoritelist.FavoriteListRepository

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class DetailTvShowViewModel(
    private val tvShowRepository: DetailTvShowRepository,
    private val favoriteListRepository: FavoriteListRepository
) : ViewModel() {
    val setToFavorite: LiveData<ResultState<Boolean>> by lazy {
        favoriteListRepository.saveResult
    }
    val deleteFavorite: LiveData<ResultState<Boolean>> by lazy {
        favoriteListRepository.deleteResult
    }
    val isFavorited: LiveData<ResultState<Boolean>> by lazy {
        favoriteListRepository.isFavoritedResult
    }

    fun setToFavorite(detailTvShows: DetailTvShows) {
        favoriteListRepository.saveFavorite(detailTvShows.detailToFavorite())
    }

    fun deleteFromFavorite(detailTvShows: DetailTvShows) {
        favoriteListRepository.deleteFavorite(detailTvShows.detailToFavorite())
    }

    fun isFavorited(id: String) {
        favoriteListRepository.isFavorited(id)
    }

    val detailTvShows: LiveData<ResultState<DetailTvShows>> by lazy {
        tvShowRepository.detailMovieResult
    }

    fun loadDetailMovie(idTvShows: String) {
        tvShowRepository.fetchDetail(idTvShows)
    }

    override fun onCleared() {
        super.onCleared()
        tvShowRepository.onCleared()
    }
}