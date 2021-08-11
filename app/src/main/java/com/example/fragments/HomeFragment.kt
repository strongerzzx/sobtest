package com.example.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.adapters.ImageAdapter
import com.example.adapters.MagicIndicatorAdapter
import com.example.adapters.TabAdapter
import com.example.base.BaseFragment
import com.example.beans.resultbeans.CategoryData
import com.example.sobdemo.HomeActivity
import com.example.sobdemo.R
import com.example.sobdemo.databinding.ActivityHomeBinding
import com.example.sobdemo.databinding.HomeFragmentLayoutBinding
import com.example.utils.ViewPager2Bind
import com.example.viewmodels.HomeViewModel
import com.youth.banner.indicator.CircleIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator

class HomeFragment : BaseFragment<HomeViewModel>() {

    private lateinit var mBinding: HomeFragmentLayoutBinding
    private lateinit var mBannearAdapter: ImageAdapter
    private lateinit var mTabAdapter: TabAdapter

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



        mTabAdapter = TabAdapter(childFragmentManager, lifecycle)
        val tabNavigator = CommonNavigator(context)
        tabNavigator.isReselectWhenLayout = false //防止文字抖动


        mViewModel.apply {

            homeBannearLiveData.observe(viewLifecycleOwner, { it ->
                Log.d(HomeActivity.TAG, "banner list size --> ${it.size}")
                it.forEach {
                    mBannerUrlList.add(it.picUrl)
                }
                mBannearAdapter = ImageAdapter(mBannerUrlList)
                mBinding.topBannear.setAdapter(mBannearAdapter)
            })


            //顶部Tab数据
            categoryListLiveData.observe(viewLifecycleOwner, {
                setupTopTab(it, tabNavigator)
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
        Log.d(HomeActivity.TAG, "category list size --> ${it.size}")
        mMagicIndicatorAdapter.setData(it, mBinding.homeFragmentViewPage2)
        mTabAdapter.setData(it)
        tabNavigator.adapter = mMagicIndicatorAdapter
        mBinding.homeFragmentMagicIndicator.navigator = tabNavigator
        ViewPager2Bind.bind(mBinding.homeFragmentMagicIndicator, mBinding.homeFragmentViewPage2)
        tabNavigator.adapter = mMagicIndicatorAdapter
        mBinding.homeFragmentMagicIndicator.navigator = tabNavigator
        mBinding.homeFragmentViewPage2.adapter = mTabAdapter
    }


    override fun getSubViewModel() = HomeViewModel::class.java

    override fun isDarkBarFont() = true


    companion object {
        private const val TAG = "HomeFragment"
    }
}