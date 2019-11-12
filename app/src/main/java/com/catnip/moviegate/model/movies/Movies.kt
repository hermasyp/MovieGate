package com.catnip.moviegate.model.movies


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Movies(
    @Json(name = "page")
    var page: Int,
    @Json(name = "results")
    var results: List<Movie>,
    @Json(name = "total_pages")
    var totalPages: Int,
    @Json(name = "total_results")
    var totalResults: Int
)