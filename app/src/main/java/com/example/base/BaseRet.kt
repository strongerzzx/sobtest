package com.example.base

data class BaseRet<T>(

    val data: T,
    val code: Int,
    val success: Boolean,
    val message: String
)


