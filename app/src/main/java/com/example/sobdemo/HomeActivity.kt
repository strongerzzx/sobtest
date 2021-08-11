package com.example.sobdemo

import android.os.Bundle
import android.util.Log
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import base.BaseActivity
import com.example.adapters.ImageAdapter
import com.example.adapters.MagicIndicatorAdapter
import com.example.adapters.TabAdapter
import com.example.beans.resultbeans.CategoryData
import com.example.sobdemo.databinding.ActivityHomeBinding
import com.example.utils.ViewPager2Bind
import com.example.viewmodels.HomeViewModel
import com.youth.banner.indicator.CircleIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator

class HomeActivity : BaseActivity<HomeViewModel>() {

    private lateinit var mBinding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        initEvent()
    }

    private fun initEvent() {
        getImmersionBar().init()

        mBinding.apply {
            setupBottomNav()
        }

    }

    private fun ActivityHomeBinding.setupBottomNav() {
        bvHomeView.itemIconTintList = null
        val navController = findNavController(R.id.navigaton_home_fragment)
        bvHomeView.setupWithNavController(navController)
    }


    override fun getSubViewModel() = HomeViewModel::class.java

    override fun isDarkBarFont() = true

    companion object {
        const val TAG = "TAG"
    }

    //防止navigation按返回无法直接退出
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}