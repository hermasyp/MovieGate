package com.catnip.moviegate.model.content

import android.os.Parcelable
import com.catnip.moviegate.data.local.entity.ContentType
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
@Parcelize
data class Content(
    @SerializedName("id")
    var id: String,
    @SerializedName(value = "title", alternate = ["name"])
    var title: String,
    @SerializedName(value = "original_title", alternate = ["original_name"])
    var originalTitle: String,
    @SerializedName(value = "release_date", alternate = ["first_air_date"])
    var releaseDate: String,
    @SerializedName("backdrop_path")
    var backdropPath: String,
    @SerializedName("genre_ids")
    var genreIds: List<Int>,
    @SerializedName("original_language")
    var originalLanguage: String,
    @SerializedName("overview")
    var overview: String,
    @SerializedName("popularity")
    var popularity: Double,
    @SerializedName("poster_path")
    var posterPath: String,
    @SerializedName("vote_average")
    var voteAverage: Double,
    @SerializedName("vote_count")
    var voteCount: Int,

    var contentType: ContentType
) : Parcelable

