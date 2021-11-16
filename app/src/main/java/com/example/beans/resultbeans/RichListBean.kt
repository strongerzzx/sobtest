package com.example.beans.resultbeans

data class RichListBean(
    val code: Int,
    val `data`: List<RichBean>,
    val message: String,
    val success: Boolean
)

data class RichBean(
    val avatar: String,
    val createTime: String,
    val nickname: String,
    val sob: Int,
    val userId: String,
    val vip: Boolean,
    var rank: Int,
    var rankIcon: Int
)