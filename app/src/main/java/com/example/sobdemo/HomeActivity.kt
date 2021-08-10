package com.example.sobdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import base.BaseActivity
import com.example.sobdemo.databinding.ActivityHomeBinding
import com.example.sobdemo.databinding.ActivityMainBinding
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



    }

    override fun getSubViewModel() = HomeViewModel::class.java

    override fun isDarkBarFont() = true

    companion object {
        const val TAG = "TAG"
    }
}