package com.example.apis

import com.example.base.BaseRet
import com.example.beans.requestbeans.CommentBean
import com.example.beans.requestbeans.SubComment
import com.example.beans.resultbeans.ArticleCommenBean
import com.example.beans.resultbeans.ArticleDetailBean
import com.example.beans.resultbeans.RichListBean
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import retrofit2.http.*

interface ArticleApiService {

    //文章详情
    @GET("/ct/article/detail/{articleId}")
    fun getArticleDetail(@Path("articleId") articleId: String): Flow<ArticleDetailBean>


    //获取文章评论列表
    @GET("/ct/article/comment/{articleId}/{page}")
    fun getArticleComment(
            @Path("articleId") articleId: String,
            @Path("page") page: Int
    ): Flow<ArticleCommenBean>


    //评论文章
    @POST("/ct/article/comment")
    fun reviewArticle(@Body commentBean: CommentBean): Flow<BaseRet<String>>

    //回复文章评论
    @POST("/ct/article/sub-comment")
    fun replySubArticle(@Body subComment: SubComment): Flow<BaseRet<String>>


    //文章打赏列表
    @GET("/ast/prise/article/{articleId}")
    fun getArticlePriseList(@Path("articleId") articleId: String): Flow<RichListBean>

}