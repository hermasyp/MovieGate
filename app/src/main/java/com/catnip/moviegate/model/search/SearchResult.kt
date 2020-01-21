package com.catnip.moviegate.model.search

import com.catnip.moviegate.model.common.Results
import com.catnip.moviegate.model.content.Content

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/

data class SearchResult(
    var movieResult: Results<Content>,
    var tvShowResults: Results<Content>
)