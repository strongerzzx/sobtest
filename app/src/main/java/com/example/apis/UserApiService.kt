package com.example.apis

import com.example.base.BaseRet
import com.example.beans.resultbeans.RichListBean
import com.example.beans.resultbeans.UserCustomAchieve
import com.example.beans.resultbeans.UserUnReadMsg
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path


interface UserApiService {

    //获取未读信息
    @GET("/ct/msg/count")
    fun getUserUnReadMsg(): Flow<UserUnReadMsg>

    //获取个人成就
    @GET("/ast/achievement")
    fun getUserAchievement(): Flow<UserCustomAchieve>

    //富豪榜
    @GET("/ast/rank/sob/{count}")
    fun getRichList(@Path("count") count: Int): Flow<RichListBean>

}