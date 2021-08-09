package com.example.beans.userinfo

/**

 * 作者：zzx on 2021/8/9 19:59

 *  作用： xxxx
 */
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