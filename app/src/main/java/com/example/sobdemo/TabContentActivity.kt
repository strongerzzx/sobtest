package com.example.sobdemo

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import base.BaseActivity
import com.example.adapters.ArticelCommentAdpater
import com.example.beans.requestbeans.CommentBean
import com.example.beans.requestbeans.SubComment
import com.example.sobdemo.databinding.ActivityTabContentBinding
import com.example.viewmodels.ArticleViewModel


class TabContentActivity : BaseActivity<ArticleViewModel>() {

    private lateinit var mBinding: ActivityTabContentBinding
    private lateinit var mCurrentArticleId: String
    private var mCurrentCommentPage = DEFAULT_COMMENT_PAGE
    private lateinit var mArticleCommentAdapter: ArticelCommentAdpater
    private lateinit var mCurrentSubComment: SubComment //二级评论 --> 点击头像的时候赋值
    private var isReply = false


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

            //TODO:发表评论 + 回复
            btnArticleReview.setOnClickListener {
                val inputComment = etArticleInputComment.text.toString()
                val commentBean = CommentBean("0", mCurrentArticleId, inputComment)
                if (inputComment.isEmpty()) return@setOnClickListener

                if (isReply) {
                    //回复
                    mViewModel.doReplySubArticle(mCurrentSubComment)
                    isReply = false
                } else {
                    //发表评论
                    mViewModel.doReviewArticle(commentBean)
                }


                Log.d(TAG, "commentBean  --> $commentBean")
            }


            //点击回复的时候
            etArticleInputComment.setOnClickListener {
                translateInputComment()
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
                        getArticleComment(mCurrentArticleId, mCurrentCommentPage)
                        Log.d(TAG, "回复评论成功 --> ")
                    }
                }
            })


            getTabArticleDetail(mCurrentArticleId)

            getArticleComment(mCurrentArticleId, mCurrentCommentPage)
        }


        mArticleCommentAdapter.setHeadCommentClickListenr { articleId, parentId, beUid, beNickname ->
            //TODO:回复
            translateInputComment()
            Log.d(TAG, "111111111111111111111111")

            val headComment = mBinding.etArticleInputComment.text.toString()
            if (headComment.isEmpty()) return@setHeadCommentClickListenr
            isReply = true
            mCurrentSubComment = SubComment(articleId, parentId, beUid, beNickname, headComment)
            Log.d(TAG, "mCurrentSubComment  -->  $mCurrentSubComment")
        }
    }

    private fun hideSoftKeyboard() {
        val view: View? = currentFocus
        if (view != null) {
            val inputMethodManager: InputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                view.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }

    private fun translateInputComment() {
        mBinding.etArticleInputComment.viewTreeObserver.addOnGlobalLayoutListener {
            val rect = Rect()
            mBinding.etArticleInputComment.getWindowVisibleDisplayFrame(rect)
            val screenHeight = mBinding.etArticleInputComment.rootView.height
            val keyHeight = screenHeight - rect.bottom
            val totalTranslateY = -keyHeight.toFloat() + 20
            mBinding.llComment.translationY = totalTranslateY

            Log.d(TAG, "111111111111111111111")
        }

        Log.d(TAG, "22222222222222222222222")

    }

    companion object {
        private const val TAG = "TabContentActivity"
        private const val DEFAULT_COMMENT_PAGE = 1
    }

    override fun getSubViewModel() = ArticleViewModel::class.java

    override fun isDarkBarFont() = true
}