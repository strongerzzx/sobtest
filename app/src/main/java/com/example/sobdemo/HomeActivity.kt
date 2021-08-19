package com.example.sobdemo

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import base.BaseActivity
import com.example.sobdemo.databinding.ActivityHomeBinding
import com.example.viewmodels.HomeViewModel

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