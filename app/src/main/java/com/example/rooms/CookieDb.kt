package com.example.rooms

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import app.SobApp


@Database(entities = [CookieBean::class], version = 1, exportSchema = false)
@TypeConverters(CookieConverter::class)
abstract class CookieDb : RoomDatabase() {

    abstract fun getCookieDao(): CookieDao

    companion object {
        private val instance: CookieDb by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            Room.databaseBuilder(SobApp.sContext, CookieDb::class.java, "cookie_db")
                .build()
        }

        fun getCookieDb(): CookieDb {
            return instance
        }
    }

}