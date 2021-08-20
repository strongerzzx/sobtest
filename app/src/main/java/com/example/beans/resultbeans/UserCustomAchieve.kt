package com.example.beans.resultbeans

data class UserCustomAchieve(
    val code: Int,
    val `data`: UserCustomAchieveData,
    val message: String,
    val success: Boolean
)

data class UserCustomAchieveData(
    val articleDxView: Int,
    val articleTotal: Int,
    val asCount: Int,
    val atotalView: Int,
    val fansCount: Int,
    val fansDx: Int,
    val favorites: Int,
    val followCount: Int,
    val id: String,
    val momentCount: Int,
    val resolveCount: Int,
    val shareDxView: Int,
    val shareTotal: Int,
    val sob: Int,
    val sobDx: Int,
    val stotalView: Int,
    val thumbUpDx: Int,
    val thumbUpTotal: Int,
    val userId: String,
    val wendaTotal: Int
)