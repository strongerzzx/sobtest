package com.example.beans

data class UserInfo(
    val code: Int,
    val `data`: Data,
    val message: String,
    val success: Boolean
)

data class Data(
    val area: String,
    val avatar: String,
    val company: String,
    val nickname: String,
    val position: String,
    val sign: String,
    val userId: String,
    val vip: Boolean
)