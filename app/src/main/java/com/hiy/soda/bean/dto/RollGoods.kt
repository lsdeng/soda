package com.hiy.soda.bean.dto

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "goods")
class RollGoods(
    @PrimaryKey
    var barcode: String = "-1",
    @ColumnInfo(name = "goods_name")
    var goodsName: String? = null,
    @ColumnInfo(name = "price")
    var price: String? = null,
    @ColumnInfo(name = "brand")
    var brand: String? = null,
    @ColumnInfo(name = "supplier")
    var supplier: String? = null,
    @ColumnInfo(name = "standard")
    var standard: String? = null
) : Parcelable