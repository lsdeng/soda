package com.hiy.soda.service

object RollService {


//    fun getBarcodeGoodsDetails(barcode: String) {
//        appId = SodaConstant.roll_app_id
//        appSecret = SodaConstant.roll_app_secret
//
//        Thread {
//            rollDb.rollGoodsDao()?.getAll()?.let {
//                Log.d("lsd-findAll", Gson().toJson(it))
//            }
//        }.start()
//        val httpUrl = HttpUrl.Builder()
//            .scheme("https")
//            .host("www.mxnzp.com")
//            .addPathSegments("/api/barcode/goods/details")
//            .addQueryParameter("barcode", barcode)
//            .build()
//        val request: Request = Request.Builder()
//            .url(httpUrl)
//            .get()
//            .build()
//
//        ServiceFactory.client.newCall(request).enqueue(object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                Log.d("lsd", e.message ?: "error")
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                val ret = response.body?.string()
//                val goodsRet = Gson().fromJson<RollResDTO<RollGoods>>(ret, object : TypeToken<RollResDTO<RollGoods>>() {}.type)
//                if (goodsRet.code == 1) {
//                    Log.d("lsd", goodsRet.data?.goodsName ?: "")
//                    goodsRet.data?.let { rollDb.rollGoodsDao()?.insertAll(it) }
//                } else {
//                    Log.d("lsd", goodsRet.msg ?: "")
//                }
//            }
//
//        })
//    }
//
//
//    /**
//     * 0 : 表示随机
//     * 1 ： 表示获取第一页数据
//     */
//    fun getGirlList(type: Int = 0) {
//        val httpUrl = HttpUrl.Builder()
//            .scheme("https")
//            .host("www.mxnzp.com")
//            .addPathSegments("/api/image/girl/list/random")
//            .build()
//        val request: Request = Request.Builder()
//            .url(httpUrl)
//            .get()
//            .build()
//
//        ServiceFactory.client.newCall(request).enqueue(object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                val retJson = response.body?.string()
//
//                val targetRet = Gson().fromJson<RollResListDTO<RollImg>>(retJson, object : TypeToken<RollResListDTO<RollImg>>() {}.type)
//                if (targetRet.code == 1) {
//                    girls.postValue(targetRet.data)
//                }
//            }
//        })
//    }


}