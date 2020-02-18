package com.catnip.mgfavapp.utils

import android.widget.TextView
import com.catnip.mgfavapp.R
import java.text.SimpleDateFormat
import java.util.*

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
fun TextView.textParseFromDate(dateString: String?) {
    dateString?.let {
        if (dateString != "") {
            var parsedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(it)
            parsedDate?.let { date ->
                text = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(date).toString()
            }
        } else {
            text = context.getString(R.string.txt_unknown)
        }
    }
}