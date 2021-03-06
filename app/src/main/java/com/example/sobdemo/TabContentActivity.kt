package com.example.sobdemo

import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import base.BaseActivity
import com.bumptech.glide.Glide
import com.example.adapters.article.ArticelCommentAdpater
import com.example.adapters.article.ArticlePriseAdapter
import com.example.beans.requestbeans.CommentBean
import com.example.beans.requestbeans.SubComment
import com.example.sobdemo.databinding.ActivityTabContentBinding
import com.example.viewmodels.ArticleViewModel
import com.gyf.immersionbar.ImmersionBar


class TabContentActivity : BaseActivity<ArticleViewModel>() {

    private lateinit var mBinding: ActivityTabContentBinding
    private lateinit var mCurrentArticleId: String
    private var mCurrentCommentPage = DEFAULT_COMMENT_PAGE
    private lateinit var mCurrentSubComment: SubComment //二级评论 --> 点击头像的时候赋值
    private var isReply = false
    private lateinit var mArticlePriseAdapter: ArticlePriseAdapter
    private lateinit var mArticleCommentAdapter: ArticelCommentAdpater


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
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

    @SuppressLint("ClickableViewAccessibility")
    private fun initEvent() {

        mArticleCommentAdapter = ArticelCommentAdpater()
        mArticlePriseAdapter = ArticlePriseAdapter()

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
            val concatConfig = ConcatAdapter.Config.Builder()
                .setIsolateViewTypes(true)
                .build()
            val concatAdapter =
                ConcatAdapter(
                    concatConfig,
                    mArticlePriseAdapter,
                    mArticleCommentAdapter
                )
            artRvComment.adapter = concatAdapter

            btnArticleReview.setOnClickListener {
                val inputComment = etArticleInputComment.text.toString()
                if (inputComment.isEmpty()) return@setOnClickListener
                if (isReply) {
                    //回复
                    mCurrentSubComment.content = inputComment
                    mViewModel.doReplySubArticle(mCurrentSubComment)
                    isReply = false
                    Log.d(TAG, "mCurrentSubComment  -->  $mCurrentSubComment")
                } else {
                    //发表评论
                    val commentBean = CommentBean("0", mCurrentArticleId, inputComment)
                    mViewModel.doReviewArticle(commentBean)
                }
                mBinding.etArticleInputComment.hint = "发表一条友善的评论..."
                hideSoftKeyboard()
            }


            etArticleInputComment.setOnTouchListener { v, event ->
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        translateInputComment()
                        Log.d(TAG, "et art input click --> ")
                    }
                    else -> {
                    }
                }
                false
            }


        }


        //加载详情内容
        mViewModel.apply {
            tabContentArticleLiveData.observe(this@TabContentActivity, Observer {
                if (it.success) {
                    if (it.data.content.isNotEmpty()) {
                        val content = it.data.content
                        mBinding.artWebView.loadArticle(content)
                        getArticleQrCode(it.data.userId)
                        Log.d(TAG, "article content --> ${it.data.content}")
                    }
                }
            })

            //获取评论
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
                Log.d(TAG, "comment")
            })

            articlePriseListLiveData.observe(this@TabContentActivity, Observer {
                if (!it.success) return@Observer
                if (!it.data.isNullOrEmpty()) {
                    mBinding.tvPriseTitle.visibility = View.VISIBLE
                    mArticlePriseAdapter.setData(it.data)
                }
            })


            reviewArticleCommentLiveData.observe(this@TabContentActivity, Observer {
                it?.let {
                    if (it.success) {
                        //评论完刷新
                        getArticleComment(mCurrentArticleId, mCurrentCommentPage)
                        Log.d(TAG, "评论成功 --> ")
                    }
                }
            })

            replyArticleCommentLiveData.observe(this@TabContentActivity, Observer {
                it?.let {
                    if (it.success) {
                        //回复完刷新
                        getArticleComment(mCurrentArticleId, mCurrentCommentPage)
                        Log.d(TAG, "回复评论成功 --> ")
                    }
                }
            })

            articleQrCodeLiveData.observe(this@TabContentActivity, Observer {
                if (it == null) {
                    return@Observer
                }
                if (!it.success) return@Observer
                if (it.data.qrcUrl.isEmpty() || it.data.qrcUrl.isBlank()) return@Observer
                //打赏码
                mBinding.ivArticleQrCode.visibility = View.VISIBLE
                Glide.with(this@TabContentActivity)
                    .load(it.data.qrcUrl)
                    .into(mBinding.ivArticleQrCode)


            })


            getTabArticleDetail(mCurrentArticleId)

            getArticleComment(mCurrentArticleId, mCurrentCommentPage)

            getArticlePriceList(mCurrentArticleId)

            Log.d(TAG, "article comment --> $mCurrentArticleId --> $mCurrentCommentPage")
        }


        mArticleCommentAdapter.setParentCommentClickListenr { articleId, parentId, beUid, beNickname ->
            //给1级评论的回复
            showSoftKeyBorad(beNickname)
            isReply = true
            mCurrentSubComment = SubComment(articleId, parentId, beUid, beNickname)
        }


        mArticleCommentAdapter.setSecCommentClickListener { articleId, parentId, beUid, beNickname ->
            //给2级评论添加的回复
            showSoftKeyBorad(beNickname)
            isReply = true
            mCurrentSubComment = SubComment(articleId, parentId, beUid, beNickname)
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

    private fun showSoftKeyBorad(content: String) {
        mBinding.etArticleInputComment.hint = "@$content "
        val inputMethodManager: InputMethodManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(
            mBinding.etArticleInputComment,
            InputMethodManager.RESULT_SHOWN
        )
        inputMethodManager.toggleSoftInput(
            InputMethodManager.SHOW_FORCED,
            InputMethodManager.HIDE_IMPLICIT_ONLY
        )
        translateInputComment()
    }


    //输入框在键盘上
    private fun translateInputComment() {
        mBinding.llComment.viewTreeObserver.addOnGlobalLayoutListener {
            val rect = Rect()
            //当前界面可见部分
            this.window.decorView.getWindowVisibleDisplayFrame(rect)
            val screengHeight = this.window.decorView.height
            //键盘高度
            val heightDiff = screengHeight - rect.bottom
            val totalTranY =
                -heightDiff.toFloat() + ImmersionBar.getStatusBarHeight(this) + (mBinding.llComment.height / 2)
            mBinding.llComment.translationY = totalTranY
            Log.d(
                TAG,
                "soft height --> $heightDiff --> $totalTranY --> ${mBinding.llComment.height}"
            )
        }
    }

    companion object {
        private const val TAG = "TabContentActivity"
        private const val DEFAULT_COMMENT_PAGE = 1
    }

    override fun getSubViewModel() = ArticleViewModel::class.java

    override fun isDarkBarFont() = true
}