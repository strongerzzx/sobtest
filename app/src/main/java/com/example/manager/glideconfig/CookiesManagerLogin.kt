package com.example.manager.glideconfig

import android.util.Log
import base.BaseRetrofit
import com.example.commonparams.CommonParms
import com.example.utils.MmkvUtil
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

class CookiesManagerLogin : CookieJar {

    private val cookieStoreLog = HashMap<String, List<Cookie>>()

    companion object {
        private const val TAG = "CookiesManagerLogin"
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
            Log.d(TAG, "login cookie --> $cookies")

            if (cookies.toString().contains("sob_token")) {
                val newCookieLogin = cookies.toString().replace("[", "")
                    .replace("]", "")
                MmkvUtil.saveString(CommonParms.SOB_TOKEN, newCookieLogin)
            }
            cookieStoreLog[BaseRetrofit.BASE_URL] = cookies
        }
    }
}