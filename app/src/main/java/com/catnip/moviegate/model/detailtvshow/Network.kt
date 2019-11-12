package com.catnip.moviegate.model.detailtvshow


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Network(
    @Json(name = "id")
    var id: Int,
    @Json(name = "logo_path")
    var logoPath: String,
    @Json(name = "name")
    var name: String,
    @Json(name = "origin_country")
    var originCountry: String
)