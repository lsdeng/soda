package com.hiy.soda.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hiy.soda.bean.dto.User
import com.hiy.soda.dao.UserDao

/**
 * auther: liusaideng
 * created on :  2023/1/9 4:26 下午
 * desc:
 */
@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}