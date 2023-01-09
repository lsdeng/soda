package com.hiy.soda.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.hiy.soda.bean.dto.User

/**
 * auther: liusaideng
 * created on :  2023/1/9 4:25 下午
 * desc:
 */
@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<User>

    @Query("SELECT * FROM user LIMIT 1")
    fun findByName(): User

    @Insert
    fun insertAll(vararg users: User)

    @Delete
    fun delete(user: User)
}