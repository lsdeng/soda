package com.hiy.soda.helper

import java.text.SimpleDateFormat
import java.util.*

object DateHelper {

    fun convertTimeToDateStr(time: Long, format: String = "yyyy-MM-DD"): String {
        return SimpleDateFormat(format).format(Date(time))
    }

    fun convertTimeToDateStr(date: Date, format: String = "yyyy-MM-DD"): String {
        return SimpleDateFormat(format).format(date)
    }
}