package com.hiy.soda.dao

import androidx.room.*
import com.hiy.soda.bean.dto.RollGoods


@Database(entities = [RollGoods::class], version = 1)
abstract class RollDatabase : RoomDatabase() {
    abstract fun rollGoodsDao(): RollGoodsDao?
}

@Dao
interface RollGoodsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg goods: RollGoods)

    @Query("SELECT * FROM goods")
    fun getAll(): List<RollGoods>

}