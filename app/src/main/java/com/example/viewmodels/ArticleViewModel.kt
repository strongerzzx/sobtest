package com.example.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apis.ArticleApiService
import com.example.base.BaseRet
import com.example.base.BaseRetrofit
import com.example.beans.requestbeans.CommentBean
import com.example.beans.requestbeans.SubComment
import com.example.beans.resultbeans.ArticleCommenBean
import com.example.beans.resultbeans.ArticleDetailBean
import com.example.beans.resultbeans.ArticleQrResult
import com.example.beans.resultbeans.RichListBean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class ArticleViewModel : ViewModel() {

    //tab详情中的文章内容
    private val _tabContentArticleLiveData = MutableLiveData<ArticleDetailBean>()
    val tabContentArticleLiveData: LiveData<ArticleDetailBean> = _tabContentArticleLiveData

    //获取文章评论
    private val _articleCommentLiveData = MutableLiveData<ArticleCommenBean>()
    val articleCommentLiveData: LiveData<ArticleCommenBean> = _articleCommentLiveData

    //文章评论
    private val _reviewArticleCommentLiveData = MutableLiveData<BaseRet<String>>()
    val reviewArticleCommentLiveData: LiveData<BaseRet<String>> = _reviewArticleCommentLiveData

    //文章回复
    private val _replyArticleCommentLiveData = MutableLiveData<BaseRet<String>>()
    val replyArticleCommentLiveData: LiveData<BaseRet<String>> = _replyArticleCommentLiveData

    //文章打赏列表
    private val __articlePriseListLiveData = MutableLiveData<RichListBean>()
    val articlePriseListLiveData: LiveData<RichListBean> = __articlePriseListLiveData

    //作者打赏码
    private val _articleQrCodeLiveData = MutableLiveData<ArticleQrResult>()
    val articleQrCodeLiveData: LiveData<ArticleQrResult?> = _articleQrCodeLiveData


    //获取文章详情
    fun getTabArticleDetail(articileId: String) {
        viewModelScope.launch(Dispatchers.Main) {
            BaseRetrofit.createApisService(ArticleApiService::class.java)
                .getArticleDetail(articileId)
                .flowOn(Dispatchers.IO)
                .collect {
                    _tabContentArticleLiveData.value = it
                    Log.d(HomeViewModel.TAG, "getTabArticleDetail  -->  $it")
                }
        }
    }

    //获取文章评论
    fun getArticleComment(articileId: String, page: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            BaseRetrofit.createApisService(ArticleApiService::class.java)
                .getArticleComment(articileId, page)
                .catch {
                    Log.d(TAG, "article  comment error --> ")
                }
                .flowOn(Dispatchers.IO)
                .collect {
                    _articleCommentLiveData.value = it
                    Log.d(TAG, "getArticleComment -->  $it")
                }
        }
    }


    //评论文章
    fun doReviewArticle(commentBean: CommentBean) {
        viewModelScope.launch(Dispatchers.Main) {
            BaseRetrofit.createApisService(ArticleApiService::class.java)
                .reviewArticle(commentBean)
                .flowOn(Dispatchers.IO)
                .catch {
                    Log.d(TAG, "doReviewArticle error --> ")
                }
                .collect {
                    _reviewArticleCommentLiveData.value = it
                    Log.d(TAG, "doReviewArticle --> $it")
                }
        }
    }

    //回复文章评论
    fun doReplySubArticle(subComment: SubComment) {
        viewModelScope.launch(Dispatchers.Main) {
            BaseRetrofit.createApisService(ArticleApiService::class.java)
                .replySubArticle(subComment)
                .flowOn(Dispatchers.IO)
                .catch {
                    Log.d(TAG, "doReviewSubArticle error --> ")
                }
                .collect {
                    _replyArticleCommentLiveData.value = it
                    Log.d(TAG, "doReviewSubArticle  --> $it")
                }
        }
    }

    //获取文章打赏列表
    fun getArticlePriceList(articileId: String) {
        viewModelScope.launch(Dispatchers.Main) {
            BaseRetrofit.createApisService(ArticleApiService::class.java)
                .getArticlePriseList(articileId)
                .flowOn(Dispatchers.IO)
                .catch {
                    Log.d(TAG, "getArticlePriceList error --> ")
                }.collect {
                    __articlePriseListLiveData.value = it
                }
        }
    }

    fun getArticleQrCode(userId: String) {
        viewModelScope.launch(Dispatchers.Main) {
            BaseRetrofit.createApisService(ArticleApiService::class.java)
                .getQrCodeByUserId(userId)
                .flowOn(Dispatchers.IO)
                .catch {
                    Log.d(TAG, "getArticleQrCode error --> ")
                }.collect {
                    if (it?.data == null) return@collect
                    if (!it.success) return@collect
                    _articleQrCodeLiveData.value = it
                }
        }
    }


    companion object {
        private const val TAG = "ArticleViewModel"
    }


}