package com.example.rooms

import androidx.room.Dao
import androidx.room.Insert
import okhttp3.Cookie


@Dao
interface CookieDao {

    @Insert
    suspend fun saveCookie(cookieList: List<Cookie>)

}