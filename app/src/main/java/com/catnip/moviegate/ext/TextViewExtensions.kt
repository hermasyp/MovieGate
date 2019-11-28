package com.catnip.moviegate.ext

import android.widget.TextView
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
fun TextView.textParseFromDate(dateString : String?){
    var parsedDate = SimpleDateFormat("yyyy-MM-dd").parse(dateString)
    text = SimpleDateFormat("dd MMM yyyy").format(parsedDate).toString()
}