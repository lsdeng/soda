package com.hiy.soda.bean.dto

import android.os.SystemClock
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "goods")
class Goods(
    @PrimaryKey()
    var id: String = "${SystemClock.elapsedRealtime()}",
    var name: String? = null,
    var brand: String? = null,
    @ColumnInfo(name = "valid_period")
    var validPeriod: Date? = null,
    val path: String? = null,
)
