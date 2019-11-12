package com.catnip.moviegate.model.tvshows


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TvShows(
    @Json(name = "page")
    var page: Int,
    @Json(name = "results")
    var results: List<TvShow>,
    @Json(name = "total_pages")
    var totalPages: Int,
    @Json(name = "total_results")
    var totalResults: Int
)