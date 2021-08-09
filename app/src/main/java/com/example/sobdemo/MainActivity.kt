package com.example.sobdemo

import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import base.BaseActivity
import com.bumptech.glide.Glide
import com.example.beans.requestbeans.UserInfo
import com.example.sobdemo.databinding.ActivityMainBinding
import com.example.utils.MD5Util
import com.example.viewmodels.UserViewModel
import java.util.*

class MainActivity : BaseActivity<UserViewModel>() {
    private lateinit var mBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        initEvent()

    }

    private fun initEvent() {
        getImmersionBar().init()

        //加载验证码
        mViewModel.checkPhoneCodePic.observe(this, {
            Glide.with(this@MainActivity)
                .load(it)
                .into(mBinding.ivYzmCheckPic)

        })


        mViewModel.loadCheckCodePic()

        mViewModel.loginLiveData.observe(this, {
            it?.let {
                if (it.success) {
                    //TODO:到详情页面
                }
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        })


        mBinding.apply {
            //点击切换验证码
            ivYzmCheckPic.setOnClickListener {
                mViewModel.loadCheckCodePic()
            }

            //登录
            btnLogin.setOnClickListener {
                val phoneNum = etInpuPhoneNum.text.toString()
                val password = etInpuPhonePassword.text.toString()
                val yzm = etInpuYzmText.text.toString()
                if (TextUtils.isEmpty(yzm) || TextUtils.isEmpty(phoneNum)
                    || TextUtils.isEmpty(password)
                ) {
                    return@setOnClickListener
                }
                mViewModel.doLogin(yzm, UserInfo(phoneNum, MD5Util.MD5(password)))
            }

            tvForgetPassword.setOnClickListener {
                readyGo(ForgetPasswordActivity::class.java)
            }



            tvRegister.setOnClickListener {
                //TODO:注册


            }

        }


    }

    override fun isDarkBarFont(): Boolean {
        return true
    }

    companion object {
        private const val TAG = "MainActivity"

    }

    override fun getSubViewModel(): Class<UserViewModel> = UserViewModel::class.java
}