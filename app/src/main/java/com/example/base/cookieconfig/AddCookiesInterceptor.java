package com.example.base.cookieconfig;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.io.IOException;

import app.SobApp;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 如果该请求存在cookie --> 则添加在请求头
 */
public class AddCookiesInterceptor implements Interceptor {

    private static final String COOKIE_PREF = "cookies_prefs";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        Request.Builder builder = request.newBuilder();
        String cookie = getCookie(request.url().toString(), request.url().host());

        if (cookie != null && !TextUtils.isEmpty(cookie)) {
            builder.addHeader("Cookie", cookie);
        }

        return chain.proceed(builder.build());
    }

    private String getCookie(String url, String host) {
        SharedPreferences sp = SobApp.sContext.getSharedPreferences(COOKIE_PREF, Context.MODE_PRIVATE);
        if (!TextUtils.isEmpty(url) && sp.contains(url) && !TextUtils.isEmpty(sp.getString(url, "")))
            return sp.getString(url, "");
        if (!TextUtils.isEmpty(host) && sp.contains(host) && !TextUtils.isEmpty(sp.getString(host, ""))) {
            return sp.getString(host, "");
        }
        return null;
    }
}
