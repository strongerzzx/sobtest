package com.example.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.base.BaseFragment
import com.example.sobdemo.R
import com.example.sobdemo.databinding.HomeFragmentLayoutBinding
import com.example.viewmodels.HomeViewModel

class HomeFragment : BaseFragment<HomeViewModel>() {

    private lateinit var mBinding: HomeFragmentLayoutBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val inflate = inflater.inflate(R.layout.home_fragment_layout, container, false)
        mBinding = HomeFragmentLayoutBinding.bind(inflate)

        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initView()
    }

    private fun initView() {
        getImmersionBar().init()
    }


    override fun getSubViewModel() = HomeViewModel::class.java

    override fun isDarkBarFont() = true


    companion object {
        private const val TAG = "HomeFragment"
    }
}