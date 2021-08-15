package com.example.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sobdemo.R
import com.gyf.immersionbar.ImmersionBar

abstract class BaseFragment<VM:ViewModel>:Fragment() {

    protected lateinit var mViewModel: VM
    private lateinit var mImmersionBar: ImmersionBar
    private var isFirstLoad = true

    companion object {
        private const val TAG = "BaseFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()

        createStatusBarConfig()
    }

    override fun onResume() {
        super.onResume()

        if (isFirstLoad){
            initDataEvent()
            isFirstLoad = false
        }
    }

     open protected fun initDataEvent(){

    }

    protected fun getImmersionBar(): ImmersionBar {
        mImmersionBar = createStatusBarConfig()
        return mImmersionBar
    }

    open fun createStatusBarConfig(): ImmersionBar {
        return ImmersionBar
            .with(this)
            .statusBarDarkFont(isDarkBarFont())
            .navigationBarColor(R.color.white)
            .fitsSystemWindows(true) //加了整个属性 --> 防止状态栏和内容叠加
    }


    private fun initViewModel() {
        mViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(getSubViewModel())
    }




    abstract fun getSubViewModel(): Class<VM>

    abstract fun isDarkBarFont(): Boolean

}