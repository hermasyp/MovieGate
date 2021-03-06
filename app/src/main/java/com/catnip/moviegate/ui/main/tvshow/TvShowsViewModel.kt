package com.catnip.moviegate.ui.main.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.catnip.moviegate.model.content.Content
import com.catnip.moviegate.data.network.PaginateResultState
import io.reactivex.disposables.CompositeDisposable

class TvShowsViewModel(private val repository: TvShowsRepository,
                       private val compositeDisposable: CompositeDisposable) : ViewModel() {

    val tvshows : LiveData<PagedList<Content>> by lazy {
        repository.fetchTvShows()
    }
    val resultState : LiveData<PaginateResultState> by lazy {
        repository.getFetchState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}