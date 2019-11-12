package com.catnip.moviegate.model.detailtvshow


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LastEpisodeToAir(
    @Json(name = "air_date")
    var airDate: String,
    @Json(name = "episode_number")
    var episodeNumber: Int,
    @Json(name = "id")
    var id: Int,
    @Json(name = "name")
    var name: String,
    @Json(name = "overview")
    var overview: String,
    @Json(name = "production_code")
    var productionCode: String,
    @Json(name = "season_number")
    var seasonNumber: Int,
    @Json(name = "show_id")
    var showId: Int,
    @Json(name = "still_path")
    var stillPath: String,
    @Json(name = "vote_average")
    var voteAverage: Double,
    @Json(name = "vote_count")
    var voteCount: Int
)