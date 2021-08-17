package com.example.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.base.BaseRetrofit
import com.example.apis.ArticleApiService
import com.example.beans.requestbeans.CommentBean
import com.example.beans.resultbeans.ArticleCommenBean
import com.example.beans.resultbeans.ArticleDetailBean
import com.example.commonparams.CommonParms
import com.example.utils.MmkvUtil
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
//    private val _reviewArticleCommentLiveData = MutableLiveData<>

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
                    .flowOn(Dispatchers.IO)
                    .catch {
                        Log.d(TAG, "article  comment error --> ")
                    }
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
                    .reviewArticle(MmkvUtil.getString(CommonParms.SOB_TOKEN), commentBean)
                    .flowOn(Dispatchers.IO)
                    .catch {
                        Log.d(TAG, "doReviewArticle error --> ")
                    }
                    .collect {
                        Log.d(TAG, "doReviewArticle --> ${it.string()}")
                    }
        }
    }


    companion object {
        private const val TAG = "ArticleViewModel"
    }


}