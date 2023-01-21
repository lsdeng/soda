package com.hiy.soda.bean.dto

import android.os.SystemClock
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * auther: liusaideng
 * created on :  2023/1/9 4:24 下午
 * desc:
 */
@Entity
data class User(
    @PrimaryKey val uid: Long = SystemClock.elapsedRealtime(),
    @ColumnInfo(name = "name") val name: String?,
)