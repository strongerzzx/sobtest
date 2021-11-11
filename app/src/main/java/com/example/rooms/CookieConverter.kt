package com.example.rooms

import androidx.room.TypeConverter
import com.blankj.utilcode.util.GsonUtils
import com.example.utils.toJson
import com.google.gson.reflect.TypeToken
import okhttp3.Cookie

class CookieConverter {

    @TypeConverter
    fun cookieToJson(cookieList: List<Cookie>) = cookieList.toJson()


    @TypeConverter
    fun jsonFromCookies(json: String): List<Cookie> {
        return GsonUtils.fromJson(json, object : TypeToken<List<Cookie>>() {}.type)
    }

}