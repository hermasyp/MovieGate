package com.catnip.moviegate.model.detailtvshow


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CreatedBy(
    @Json(name = "credit_id")
    var creditId: String,
    @Json(name = "gender")
    var gender: Int,
    @Json(name = "id")
    var id: Int,
    @Json(name = "name")
    var name: String,
    @Json(name = "profile_path")
    var profilePath: String
)