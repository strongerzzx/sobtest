package com.example.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.adapters.ImageAdapter
import com.example.adapters.MagicIndicatorAdapter
import com.example.adapters.HomeTabAdapter
import com.example.base.BaseFragment
import com.example.beans.resultbeans.CategoryData
import com.example.sobdemo.R
import com.example.sobdemo.databinding.HomeFragmentLayoutBinding
import com.example.utils.ViewPager2Bind
import com.example.viewmodels.HomeViewModel
import com.example.viewmodels.MoYuViewModel
import com.youth.banner.indicator.CircleIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator

class HomeFragment : BaseFragment<HomeViewModel>() {

    private lateinit var mBinding: HomeFragmentLayoutBinding
    private lateinit var mBannearAdapter: ImageAdapter
    private lateinit var mHomeTabAdapter: HomeTabAdapter

    private val mBannerUrlList = mutableListOf<String>()

    private val mMagicIndicatorAdapter by lazy {
        MagicIndicatorAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val inflate = inflater.inflate(R.layout.home_fragment_layout, container, false)
        mBinding = HomeFragmentLayoutBinding.bind(inflate)

        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initEvent()
    }

    private fun initEvent() {
        getImmersionBar().init()

        mHomeTabAdapter = HomeTabAdapter(childFragmentManager, lifecycle)
        val tabNavigator = CommonNavigator(context)
        tabNavigator.isReselectWhenLayout = false //防止文字抖动

        mViewModel.apply {

            homeBannearLiveData.observe(viewLifecycleOwner, { it ->
                Log.d(TAG, "banner list size --> ${it.size}")
                it.forEach {
                    mBannerUrlList.add(it.picUrl)
                }
                mBannearAdapter = ImageAdapter(mBannerUrlList)
                mBinding.topBannear.setAdapter(mBannearAdapter)
            })


            //顶部Tab数据
            categoryListLiveData.observe(viewLifecycleOwner, {
                setupTopTab(it, tabNavigator)

                //配合base在onResume判断 是否第一次加载
                mBinding.homeFragmentViewPage2.offscreenPageLimit = it.size
            })


            getHomeBannearList()

            getCategoryList()

        }

        mBinding.apply {
            setupBannear()
        }

    }

    private fun HomeFragmentLayoutBinding.setupBannear() {
        topBannear.addBannerLifecycleObserver(viewLifecycleOwner)
        topBannear.setBannerRound(5f)
        topBannear.setBannerGalleryMZ(5)
        topBannear.indicator = CircleIndicator(context)
    }

    private fun setupTopTab(
            it: List<CategoryData>,
            tabNavigator: CommonNavigator
    ) {
        Log.d(TAG, "category list size --> ${it.size}")
        mMagicIndicatorAdapter.setData(it, mBinding.homeFragmentViewPage2)
        mHomeTabAdapter.setData(it)
        tabNavigator.adapter = mMagicIndicatorAdapter
        mBinding.homeFragmentMagicIndicator.navigator = tabNavigator
        ViewPager2Bind.bind(mBinding.homeFragmentMagicIndicator, mBinding.homeFragmentViewPage2)
        mBinding.homeFragmentMagicIndicator.navigator = tabNavigator
        mBinding.homeFragmentViewPage2.adapter = mHomeTabAdapter
    }


    override fun getSubViewModel() = HomeViewModel::class.java

    override fun isDarkBarFont() = true


    companion object {
        private const val TAG = "HomeFragment"
    }
}