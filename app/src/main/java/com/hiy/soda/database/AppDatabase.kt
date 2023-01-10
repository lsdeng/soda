package com.hiy.soda.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.hiy.soda.bean.dto.Goods
import com.hiy.soda.bean.dto.User
import com.hiy.soda.dao.GoodsDao
import com.hiy.soda.dao.UserDao


/**
 * auther: liusaideng
 * created on :  2023/1/9 4:26 下午
 * desc:
 */
@Database(

    entities = [
        User::class,
        Goods::class
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    abstract fun goodsDao(): GoodsDao
}


