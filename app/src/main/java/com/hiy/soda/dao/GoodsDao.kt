package com.hiy.soda.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.hiy.soda.bean.dto.Goods

/**
 * auther: liusaideng
 * created on :  2023/1/9 4:25 下午
 * desc:
 */
@Dao
interface GoodsDao {
    @Query("SELECT * FROM goods order by id desc ")
    fun getAll(): List<Goods>

    @Insert
    fun insertAll(vararg goods: Goods)


    @Query("DELETE FROM goods WHERE id=:goodsId")
    fun delete(goodsId: String)
}