package com.catnip.moviegate.model.detailmovie


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Genre(
    @Json(name = "id")
    var id: Int,
    @Json(name = "name")
    var name: String
)