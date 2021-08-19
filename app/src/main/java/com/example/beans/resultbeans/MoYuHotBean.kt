package com.example.beans.resultbeans

data class MoYuHotBean(
    val code: Int, val `data`: List<MoYuHotData>, val message: String, val success: Boolean
)

data class MoYuHotData(
    val avatar: String,
    val commentCount: Int,
    val company: String,
    val content: String,
    val createTime: String,
    val hasThumbUp: Boolean,
    val id: String,
    val images: List<String>,
    val linkCover: String,
    val linkTitle: String,
    val linkUrl: String,
    val nickname: String,
    val position: String,
    val thumbUpCount: Int,
    val thumbUpList: List<String>,
    val topicId: String,
    val topicName: String,
    val userId: String,
    val vip: Boolean
)
