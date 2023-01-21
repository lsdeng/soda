package com.hiy.soda.database

import androidx.room.Room
import com.hiy.soda.SodaApp

/**
 * auther: liusaideng
 * created on :  2023/1/10 8:40 下午
 * desc:
 */
object DBHelper {
    val database by lazy {
        Room.databaseBuilder(
            SodaApp.getContext(),
            AppDatabase::class.java, "database-name"
        )
            .build()
    }
}