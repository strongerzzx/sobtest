package com.example.beans.resultbeans

data class UserUnReadMsg(
    val code: Int,
    val `data`: UserUnReadMsgData,
    val message: String,
    val success: Boolean
)

data class UserUnReadMsgData(
    val articleMsgCount: Int,
    val atMsgCount: Int,
    val momentCommentCount: Int,
    val shareMsgCount: Int,
    val systemMsgCount: Int,
    val thumbUpMsgCount: Int,
    val wendaMsgCount: Int
)