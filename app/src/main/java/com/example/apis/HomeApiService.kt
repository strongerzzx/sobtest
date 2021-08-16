package com.example.apis

import com.example.base.BaseRet
import com.example.beans.resultbeans.*
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
    fun getCategoryList(): Flow<CategoryBean>

    //获取tab下的列表
    @GET("/ct/content/home/recommend/{categoryId}/{page}")
    fun getHomeSubTabList(
        @Path("categoryId") categoryId: String,
        @Path("page") page: Int
    ): Flow<HomeTabSubBean>

    //获取推荐内容
    @GET("/ct/content/home/recommend/{page}")
    fun getRecommend(@Path("page") page: String): Flow<ResponseBody>


    //文章详情
    @GET("/ct/article/detail/{articleId}")
    fun getArticleDetail(@Path("articleId") articleId: String):Flow<ArticleDetailBean>

}