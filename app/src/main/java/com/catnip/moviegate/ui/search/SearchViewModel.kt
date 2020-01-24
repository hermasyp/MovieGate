package com.catnip.moviegate.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.catnip.moviegate.data.network.ResultState
import com.catnip.moviegate.model.search.SearchResult

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/

class SearchViewModel(private val repo: SearchRepository) : ViewModel() {
    val searchResult: LiveData<ResultState<SearchResult>> by lazy {
        repo.searchResult
    }

    fun getSearchResult(query: String) {
        repo.fetchSearchResult(query)
    }

    override fun onCleared() {
        super.onCleared()
        repo.onClear()
    }
}