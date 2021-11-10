package com.example.rooms

import androidx.room.Entity
import androidx.room.PrimaryKey
import okhttp3.Cookie


@Entity
data class CookieBean(
    @PrimaryKey
    val host: String,

    val cookieList: List<Cookie> = listOf()
)