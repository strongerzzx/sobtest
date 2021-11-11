package com.example.rooms

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Transaction
import okhttp3.Cookie


@Dao
interface CookieDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCookie(cookieList: List<CookieBean>)

}