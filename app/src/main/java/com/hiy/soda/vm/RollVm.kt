package com.hiy.soda.vm

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hiy.soda.SodaApp
import com.hiy.soda.bean.common.RollResDTO
import com.hiy.soda.bean.common.RollResListDTO
import com.hiy.soda.bean.dto.RollGoods
import com.hiy.soda.bean.dto.RollImg
import com.hiy.soda.helper.SodaConstant
import okhttp3.*
import java.io.IOException

class RollVm : ViewModel() {

    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(Interceptor { chain ->
                val requestBuilder = chain.request().newBuilder()
                chain.request().apply {
                    val httpUrl = this.url.newBuilder().addQueryParameter("app_id", SodaConstant.roll_app_id)
                        .addQueryParameter("app_secret", SodaConstant.roll_app_secret).build()
                    requestBuilder.url(httpUrl)
                }
                chain.proceed(requestBuilder.build())
            })
            .build()
    }

    lateinit var appId: String

    lateinit var appSecret: String

    val goods = MutableLiveData<List<RollGoods>>(listOf())

    val girls = MutableLiveData<List<RollImg>>(listOf())


    fun attachGoods() {
    }

    fun getBarcodeGoodsDetails(barcode: String) {
        appId = SodaConstant.roll_app_id
        appSecret = SodaConstant.roll_app_secret

        val httpUrl = HttpUrl.Builder()
            .scheme("https")
            .host("www.mxnzp.com")
            .addPathSegments("/api/barcode/goods/details")
            .addQueryParameter("barcode", barcode)
            .build()
        val request: Request = Request.Builder()
            .url(httpUrl)
            .get()
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("lsd", e.message ?: "error")
            }

            override fun onResponse(call: Call, response: Response) {
                val ret = response.body?.string()
                val goodsRet = Gson().fromJson<RollResDTO<RollGoods>>(ret, object : TypeToken<RollResDTO<RollGoods>>() {}.type)
                if (goodsRet.code == 1) {
                    Log.d("lsd", goodsRet.data?.goodsName ?: "")
                } else {
                    Log.d("lsd", goodsRet.msg ?: "")
                }
            }

        })
    }


    /**
     * 0 : 表示随机
     * 1 ： 表示获取第一页数据
     */
    fun getGirlList(type: Int = 0) {
        val httpUrl = HttpUrl.Builder()
            .scheme("https")
            .host("www.mxnzp.com")
            .addPathSegments("/api/image/girl/list/random")
            .build()
        val request: Request = Request.Builder()
            .url(httpUrl)
            .get()
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                val retJson = response.body?.string()

                val targetRet = Gson().fromJson<RollResListDTO<RollImg>>(retJson, object : TypeToken<RollResListDTO<RollImg>>() {}.type)
                if (targetRet.code == 1) {
                    girls.postValue(targetRet.data)
                }
            }
        })
    }


}