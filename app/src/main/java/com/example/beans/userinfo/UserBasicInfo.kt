package com.example.beans.userinfo

import com.google.gson.annotations.SerializedName

/***
 * 用户最基本的信息
 */
data class UserBasicInfo(
        @JvmField
        @SerializedName("fansCount")
        val fansCount: Any,
        @JvmField
        @SerializedName("followCount")
        val followCount: Any,
        @JvmField
        @SerializedName("id")
        val id: String,
        @JvmField
        @SerializedName("isVip")
        val isVip: String,
        @JvmField
        @SerializedName("lev")
        val lev: Int,
        @JvmField
        @SerializedName("nickname")
        val nickname: String,
        @JvmField
        @SerializedName("roles")
        val roles: String,
        @JvmField
        @SerializedName("token")
        val token: String
)