package com.example.sobdemo

import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.text.method.ScrollingMovementMethod
import android.util.Log
import androidx.lifecycle.Observer
import app.SobApp
import base.BaseActivity
import com.example.sobdemo.databinding.ActivityTabContentBinding
import com.example.utils.markdowm.MyGrammarLocator
import com.example.viewmodels.HomeViewModel
import io.noties.markwon.Markwon
import io.noties.markwon.html.HtmlPlugin
import io.noties.markwon.image.glide.GlideImagesPlugin
import io.noties.markwon.syntax.Prism4jThemeDarkula
import io.noties.markwon.syntax.SyntaxHighlightPlugin
import io.noties.prism4j.Prism4j


class TabContentActivity : BaseActivity<HomeViewModel>() {

    private lateinit var mBinding: ActivityTabContentBinding
    private lateinit var mCurrentArticleId: String


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
        getImmersionBar().init()
    }

    private fun initEvent() {

        mBinding.apply {
            tabContentTopReturn.ivCommonBack.setOnClickListener {
                finish()
            }

            //设置TextView重置滑动
            tvTabContentArticle.movementMethod = ScrollingMovementMethod.getInstance()

        }


        //加载详情内容
        mViewModel.apply {

            tabContentArticleLiveData.observe(this@TabContentActivity, Observer {
                if (it.success) {
                    if (it.data.content.isNotEmpty()) {
                        val content = it.data.content
                        val prism4jTheme = Prism4jThemeDarkula.create(Color.parseColor("#7C7C7C"))
                        val markwon = Markwon.builder(SobApp.sContext)
                            .usePlugin(HtmlPlugin.create())
                            .usePlugin(
                                SyntaxHighlightPlugin.create(
                                    Prism4j(MyGrammarLocator()),
                                    prism4jTheme
                                )
                            )
                            .usePlugin(GlideImagesPlugin.create(SobApp.sContext))
                            .build()
                        markwon.setMarkdown(mBinding.tvTabContentArticle, content ?: "")



                        Log.d(TAG, "article content --> ${it.data.content}")
                    }
                }
            })
            getTabArticleDetail(mCurrentArticleId)

        }

    }


    companion object {
        private const val TAG = "TabContentActivity"
    }

    override fun getSubViewModel() = HomeViewModel::class.java

    override fun isDarkBarFont() = true
}