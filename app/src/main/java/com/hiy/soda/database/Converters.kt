package com.hiy.soda.database

import androidx.room.TypeConverter
import java.util.*

/**
 * auther: liusaideng
 * created on :  2023/1/10 8:01 下午
 * desc:
 */
class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }
}