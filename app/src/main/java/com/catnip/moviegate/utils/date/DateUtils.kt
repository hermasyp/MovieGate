package com.catnip.moviegate.utils.date

import java.text.SimpleDateFormat
import java.util.*

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/

object DateUtils {
    fun getFormattedTimeNow():String{
        val dtf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dtf.format(Date())
    }
}