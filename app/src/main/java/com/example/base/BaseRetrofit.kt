package com.example.base

import com.chenxyu.retrofit.adapter.BuildConfig
import com.chenxyu.retrofit.adapter.FlowCallAdapterFactory
import com.example.commonparams.CommonParms
import com.example.manager.TokenHeaderInterceptor
import com.example.manager.glideconfig.CookiesManagerGlide
import com.example.manager.glideconfig.CookiesManagerLogin
import com.example.utils.MmkvUtil
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**

 * 作者：zzx on 2021/8/7 19:03

 *  作用： xxxx
 */
object BaseRetrofit {

    const val BASE_URL = "https://api.sunofbeach.net"

    fun getGlideOkHttpClient(): OkHttpClient {
        val builder: OkHttpClient.Builder = OkHttpClient.Builder()
                .cookieJar(CookiesManagerGlide()) //cookieGlideJar
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            builder.addInterceptor(httpLoggingInterceptor.apply {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            })
        }
        return builder.build()
    }

    private fun getLoginOkHttpClient(): OkHttpClient {
        val builder: OkHttpClient.Builder = OkHttpClient.Builder()
                .cookieJar(CookiesManagerLogin())
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addNetworkInterceptor(TokenHeaderInterceptor())
        return builder.build()
    }

    private fun getRetrofit(BASE_URL: String) = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(FlowCallAdapterFactory.create())
            .client(getLoginOkHttpClient())
            .build()

    fun <T> createApisService(ApiService: Class<T>): T = getRetrofit(BASE_URL).create(ApiService)

}