package com.example.manager.glideconfig

import android.util.Log
import com.example.base.BaseRetrofit
import com.example.commonparams.CommonParms
import com.example.utils.MmkvUtil
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

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
        val sob = cookieStoreLog[BaseRetrofit.BASE_URL]
        sob?.let {
            for (cookie in it) {
                println(cookie)
            }
            return it
        }
        return list
    }

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        Log.d(TAG, "saveFromResponse HttpUrl --> $url  --> ${url.host}")
        if (BaseRetrofit.BASE_URL.contains(url.host)) {
            for (cookie in cookies) {
                Log.d(TAG, "cookie --> $cookie")
            }

            val newCookies = cookies.toString().replace("[", "")
                .replace("]", "")
            Log.d(TAG, "cookie2 --> $newCookies")

            MmkvUtil.saveString(CommonParms.COOKIE_PIC_KEY, newCookies)
            cookieStoreLog[BaseRetrofit.BASE_URL] = cookies
        }
    }
}