package com.example.manager.glideconfig

import android.util.Log
import android.webkit.CookieManager
import com.example.base.BaseRetrofit
import com.example.commonparams.CommonParms
import com.example.utils.MmkvUtil
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

/**

 * 作者：zzx on 2021/8/8 20:05

 *  作用： xxxx
 */
class CookiesManagerGlide : CookieJar {

    private val cookieStoreLog = HashMap<String, List<Cookie>>()

    companion object {
        private const val TAG = "CookiesManagerGlide"
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        val list = ArrayList<Cookie>()

        val localCookie = MmkvUtil.getString(CommonParms.COOKIE_PIC_KEY)

        Log.d(TAG, "save cookie --> $localCookie")
        if (cookieStoreLog.isNotEmpty()) {
            val sob = cookieStoreLog[url.host]
            return sob!!
        }
        return list
    }

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        Log.d(TAG, "saveFromResponse HttpUrl --> $url  --> ${url.host}")
        if (cookies.isNullOrEmpty()) return

        if (BaseRetrofit.BASE_URL.contains(url.host)) {
            MmkvUtil.saveString(CommonParms.COOKIE_PIC_KEY, cookies.toString())
            cookieStoreLog[url.host] = cookies
        }

        //TODO:存储在数据库

        val cm = CookieManager.getInstance()
        if (cookieStoreLog.isNotEmpty()) {
            val list = cookieStoreLog[url.host]
            list?.forEach {
                cm.setCookie(url.host, it.value)
            }
            cm.flush()
        }
    }


}