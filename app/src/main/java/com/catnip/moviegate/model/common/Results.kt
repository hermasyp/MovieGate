package com.catnip.moviegate.model.common

import com.google.gson.annotations.SerializedName

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
data class Results<T>(
    @SerializedName("page")
    var page: Int,
    @SerializedName("results")
    var datas: MutableList<T>,
    @SerializedName("total_pages")
    var totalPages: Int,
    @SerializedName("total_results")
    var totalResults: Int
)