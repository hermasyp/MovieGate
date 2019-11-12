package com.catnip.moviegate.model.detailtvshow


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Season(
    @Json(name = "air_date")
    var airDate: String,
    @Json(name = "episode_count")
    var episodeCount: Int,
    @Json(name = "id")
    var id: Int,
    @Json(name = "name")
    var name: String,
    @Json(name = "overview")
    var overview: String,
    @Json(name = "poster_path")
    var posterPath: String,
    @Json(name = "season_number")
    var seasonNumber: Int
)