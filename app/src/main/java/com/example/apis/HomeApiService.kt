package com.example.apis

import com.example.base.BaseRet
import com.example.beans.resultbeans.BannearBean
import com.example.beans.resultbeans.CategoryBean
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path


interface HomeApiService {

    //首页轮播图
    @GET("/ast/home/loop/list")
    fun getHomeBannear(): Flow<BannearBean>


    //获取分类列表
    @GET("/ct/category/list")
    fun getCategoryList():Flow<CategoryBean>


    //获取推荐内容
    @GET("/ct/content/home/recommend/{page}")
    fun getRecommend(@Path("page")page:String):Flow<ResponseBody>

}