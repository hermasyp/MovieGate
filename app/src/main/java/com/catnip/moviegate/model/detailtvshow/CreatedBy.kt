package com.catnip.moviegate.model.detailtvshow


import com.google.gson.annotations.SerializedName

data class CreatedBy(
    @SerializedName("credit_id")
    var creditId: String,
    @SerializedName("gender")
    var gender: Int,
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("profile_path")
    var profilePath: String
)