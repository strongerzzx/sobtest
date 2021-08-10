package com.example.beans.resultbeans

data class BannearBean(
    val code: Int,
    val `data`: List<BannearData>,
    val message: String,
    val success: Boolean
)

data class BannearData(
    val createTime: String,
    val picUrl: String,
    val targetUrl: String,
    val type: String
)