package com.example.utils

import com.blankj.utilcode.util.GsonUtils

/**
 * 超类 --> 所有对象都能实现转换成json
 */
fun Any.toJson() = GsonUtils.toJson(this)


inline fun <reified T> Any.fromJson(json: String?) = GsonUtils.fromJson<T>(json, T::class.java)