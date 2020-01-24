package com.catnip.moviegate.ui.search

import com.catnip.moviegate.data.local.entity.ContentType
import com.catnip.moviegate.model.content.Content
import com.catnip.moviegate.model.search.SearchResult

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/

fun SearchResult.mapToContents(): MutableList<Content> {
    var resultMovie = this.movieResult.datas
    resultMovie.forEach {
        it.contentType = ContentType.MOVIE
    }
    var resultTvShows = this.tvShowResults.datas
    resultTvShows.forEach {
        it.contentType = ContentType.TVSHOWS
    }
    val nameComperator = compareBy<Content> { it.title }
    return (resultMovie + resultTvShows).sortedWith(nameComperator).toMutableList()
}