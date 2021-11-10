package com.example.base.cookieconfig;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import app.SobApp;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 获取cookie并保存
 */
public class SaveCookieIntercept implements Interceptor {

    private static final String COOKIE_PREF = "cookies_prefs";

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request(); //获取拦截请求
        Response response = chain.proceed(request); //获取请求的响应

        if (!response.headers("set-cookie").isEmpty()) {
            List<String> cookieList = response.headers("set-cookie");
            String cookie = encodeCookie(cookieList);
            saveCookie(request.url().toString(), request.url().host().toString(), cookie);
        }
        return response;
    }

    private void saveCookie(String url, String host, String cookie) {
        SharedPreferences sp = SobApp.sContext.getSharedPreferences(COOKIE_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        if (!TextUtils.isEmpty(url)) {
            edit.putString(url, cookie);
        }
        if (!TextUtils.isEmpty(host)) {
            edit.putString(host, cookie);
        }
        edit.apply();
    }

    private String encodeCookie(List<String> cookieList) {
        StringBuilder sb = new StringBuilder();
        Set<String> set = new HashSet<>();
        for (String cookie : cookieList) {
            String[] arr = cookie.split(";");
            for (String s : arr) {
                if (set.contains(s)) continue;
                set.add(s);
            }
        }

        Iterator<String> ite = set.iterator();
        while (ite.hasNext()) {
            String cookie = ite.next();
            sb.append(cookie).append(";");
        }

        int last = sb.lastIndexOf(";");
        if (sb.length() - 1 == last) {
            sb.deleteCharAt(last);
        }
        return sb.toString();
    }
}
