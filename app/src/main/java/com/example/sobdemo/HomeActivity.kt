package com.example.sobdemo

import android.os.Bundle
import android.util.Log
import androidx.viewpager2.widget.ViewPager2
import base.BaseActivity
import com.example.adapters.ImageAdapter
import com.example.adapters.MagicIndicatorAdapter
import com.example.adapters.TabAdapter
import com.example.beans.resultbeans.CategoryData
import com.example.sobdemo.databinding.ActivityHomeBinding
import com.example.utils.ViewPager2Bind
import com.example.viewmodels.HomeViewModel
import com.youth.banner.indicator.CircleIndicator
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator

class HomeActivity : BaseActivity<HomeViewModel>() {

    private lateinit var mBinding: ActivityHomeBinding
    private lateinit var mBannearAdapter: ImageAdapter
    private lateinit var mTabAdapter: TabAdapter

    private val mBannerUrlList = mutableListOf<String>()

    private val mMagicIndicatorAdapter by lazy {
        MagicIndicatorAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        initEvent()
    }

    private fun initEvent() {
        getImmersionBar().init()

        mTabAdapter = TabAdapter(supportFragmentManager, lifecycle)
        val tabNavigator = CommonNavigator(this@HomeActivity)
        tabNavigator.isReselectWhenLayout = false //防止文字抖动


        mViewModel.apply {

            homeBannearLiveData.observe(this@HomeActivity, { it ->
                Log.d(TAG, "banner list size --> ${it.size}")
                it.forEach {
                    mBannerUrlList.add(it.picUrl)
                }
                mBannearAdapter = ImageAdapter(mBannerUrlList)
                mBinding.topBannear.setAdapter(mBannearAdapter)
            })


            //顶部Tab数据
            categoryListLiveData.observe(this@HomeActivity, {
                setupTopTab(it, tabNavigator)
            })


            getHomeBannearList()

            getCategoryList()


        }


        mBinding.apply {
            setupBannear()
        }

    }

    private fun setupTopTab(
        it: List<CategoryData>,
        tabNavigator: CommonNavigator
    ) {
        Log.d(TAG, "category list size --> ${it.size}")
        mMagicIndicatorAdapter.setData(it, mBinding.viewPage2)
        mTabAdapter.setData(it)
        tabNavigator.adapter = mMagicIndicatorAdapter
        mBinding.magicIndicator.navigator = tabNavigator
        ViewPager2Bind.bind(mBinding.magicIndicator, mBinding.viewPage2)
        tabNavigator.adapter = mMagicIndicatorAdapter
        mBinding.magicIndicator.navigator = tabNavigator
        mBinding.viewPage2.adapter = mTabAdapter
    }


    private fun ActivityHomeBinding.setupBannear() {
        topBannear.addBannerLifecycleObserver(this@HomeActivity)
        topBannear.setBannerRound(5f)
        topBannear.setBannerGalleryMZ(5)
        topBannear.indicator = CircleIndicator(this@HomeActivity)
    }

    override fun getSubViewModel() = HomeViewModel::class.java

    override fun isDarkBarFont() = true

    companion object {
        const val TAG = "TAG"
    }
}