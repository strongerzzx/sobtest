package com.example.beans.resultbeans

/**

 * 作者：zzx on 2021/8/15 13:17

 *  作用： xxxx
 */
data class HomeTabSubBean(
    val code: Int,
    val `data`: HomeSubData,
    val message: String,
    val success: Boolean
)

data class HomeSubData(
    val list: List<HomeSubItem>
)

data class HomeSubItem(
    val avatar: String,
    val covers: List<String>,
    val createTime: String,
    val id: String,
    val nickName: String,
    val thumbUp: Int,
    val title: String,
    val type: Int,
    val userId: String,
    val viewCount: Int,
    val vip: Boolean
)