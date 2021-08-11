package com.example.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.base.BaseFragment
import com.example.sobdemo.R
import com.example.sobdemo.databinding.ArticleFragmentLayoutBinding
import com.example.viewmodels.ArticleViewModel

class ArticleFragment : BaseFragment<ArticleViewModel>() {

    private lateinit var mBinding: ArticleFragmentLayoutBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val inflate = inflater.inflate(R.layout.article_fragment_layout, container, false)
        mBinding = ArticleFragmentLayoutBinding.bind(inflate)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initEvent()
    }

    private fun initEvent() {
        getImmersionBar().init()
    }

    override fun getSubViewModel() = ArticleViewModel::class.java

    override fun isDarkBarFont() = true
}