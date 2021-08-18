package com.example.sobdemo

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import base.BaseActivity
import com.example.adapters.ArticelCommentAdpater
import com.example.beans.requestbeans.CommentBean
import com.example.beans.requestbeans.SubComment
import com.example.sobdemo.databinding.ActivityTabContentBinding
import com.example.viewmodels.ArticleViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class TabContentActivity : BaseActivity<ArticleViewModel>() {

    private lateinit var mBinding: ActivityTabContentBinding
    private lateinit var mCurrentArticleId: String
    private var mCurrentCommentPage = DEFAULT_COMMENT_PAGE
    private lateinit var mArticleCommentAdapter: ArticelCommentAdpater


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityTabContentBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        initImmberBar()

        initPreData()

        initEvent()

    }

    private fun initPreData() {
        mCurrentArticleId = intent.getStringExtra("contentId").toString()

        mBinding.tabContentTopReturn.tvCommomTitle.text = "文章详情"

        Log.d(TAG, "initPreData  --> $mCurrentArticleId")
    }

    private fun initImmberBar() {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        getImmersionBar().init()
    }

    private fun initEvent() {

        mArticleCommentAdapter = ArticelCommentAdpater()

        mBinding.apply {
            tabContentTopReturn.ivCommonBack.setOnClickListener {
                hideSoftKeyboard()
                finish()
            }
            artWebView.setWebViewScrollView(artWebScroll)

            artWebView.setOpenUrlListener {

            }

            artWebScroll.setOverScrollListener {
                artRvComment.isNestedScrollingEnabled = it

            }

            artRvComment.layoutManager = LinearLayoutManager(this@TabContentActivity)
            artRvComment.adapter = mArticleCommentAdapter

            //发表评论
            btnArticleReview.setOnClickListener {
                val commentBean = CommentBean("0", mCurrentArticleId, etArticleInputComment.text.toString())

                Log.d(TAG, "commentBean  --> $commentBean")

                mViewModel.doReviewArticle(commentBean)
            }


            //点击回复的时候
            etArticleInputComment.setOnClickListener {
                translateInputComment(llComment, etArticleInputComment)
            }

        }


        //加载详情内容
        mViewModel.apply {

            tabContentArticleLiveData.observe(this@TabContentActivity, Observer {
                if (it.success) {
                    if (it.data.content.isNotEmpty()) {
                        val content = it.data.content
                        mBinding.artWebView.loadArticle(content)
                        Log.d(TAG, "article content --> ${it.data.content}")
                    }
                }
            })

            //评论
            articleCommentLiveData.observe(this@TabContentActivity, Observer {
                if (!it.success) return@Observer
                //一共页数
                val totalPages = it.data.totalPages
                val contentCommom = it.data.content
                if (contentCommom.isNotEmpty()) {
                    mArticleCommentAdapter.setData(contentCommom)
                } else {
                    Toast.makeText(this@TabContentActivity, "暂无评论", Toast.LENGTH_SHORT).show()
                }
            })

            reviewArticleCommentLiveData.observe(this@TabContentActivity, Observer {
                it?.let {
                    if (it.success) {
                        getArticleComment(mCurrentArticleId, mCurrentCommentPage)
                        Log.d(TAG, "评论成功 --> ")
                    }
                }
            })

            replyArticleCommentLiveData.observe(this@TabContentActivity, Observer {
                //回复
                it?.let {
                    if (it.success) {

                        Log.d(TAG,"回复成功 --> ")
                    }
                }
            })



            getTabArticleDetail(mCurrentArticleId)

            getArticleComment(mCurrentArticleId, mCurrentCommentPage)
        }


        mArticleCommentAdapter.setHeadCommentClickListenr { articleId, parentId, beUid, beNickname ->
            //TODO:一级评论
            val headComment = mBinding.etArticleInputComment.text.toString()
            val subCommentBean = SubComment(articleId, parentId, beUid, beNickname, headComment)

            Log.d(TAG, "subCommentBean  --> $subCommentBean")
            mViewModel.doReviewSubArticle(subCommentBean)

        }

    }

    private fun hideSoftKeyboard() {
        val view: View? = currentFocus
        if (view != null) {
            val inputMethodManager: InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    private fun translateInputComment(llComment: LinearLayout, etArticleInputComment: EditText) {
        etArticleInputComment.viewTreeObserver.addOnGlobalLayoutListener {
            val rect = Rect()
            etArticleInputComment.getWindowVisibleDisplayFrame(rect)
            val screenHeight = etArticleInputComment.rootView.height
            val keyHeight = screenHeight - rect.bottom
            val totalTranslateY = -keyHeight.toFloat() + 20
            llComment.translationY = totalTranslateY
        }
    }

    companion object {
        private const val TAG = "TabContentActivity"
        private const val DEFAULT_COMMENT_PAGE = 1
    }

    override fun getSubViewModel() = ArticleViewModel::class.java

    override fun isDarkBarFont() = true
}