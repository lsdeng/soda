package com.hiy.soda.service

import com.hiy.soda.helper.SodaConstant
import okhttp3.Interceptor
import okhttp3.OkHttpClient

object ServiceFactory {
     val client: OkHttpClient by lazy {
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
}