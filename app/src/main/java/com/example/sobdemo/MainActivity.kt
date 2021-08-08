package com.example.sobdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import base.BaseActivity
import base.BaseRetrofit
import beans.requestbeans.SendSmsVo
import com.bumptech.glide.Glide
import com.example.sobdemo.databinding.ActivityMainBinding
import viewmodels.LoginViewModel
import java.util.*

class MainActivity : BaseActivity<LoginViewModel>() {
    private lateinit var mBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        initEvent()

    }

    private fun initEvent() {

        //加载验证码
        mViewModel.checkPhoneCodePic.observe(this,{
            Glide.with(this@MainActivity)
                .load(it)
                .into(  mBinding.ivRegisterCheckPic)

        })

        mViewModel.loadCheckCodePic()

        mBinding.apply {
            //点击切换验证码
            ivRegisterCheckPic.setOnClickListener {
                mViewModel.loadCheckCodePic()
            }


            mViewModel.registerLivedata.observe(this@MainActivity,{
                Toast.makeText(this@MainActivity,it.message,Toast.LENGTH_SHORT).show()
            })

            btnRegister.setOnClickListener {
                mViewModel.getPhoneCheckCode(SendSmsVo(etRegisterAccount.text.toString()
                        , etRegisterCheckPhone.text.toString()))
                Log.d(TAG,"resgister params --> ${etRegisterAccount.text.toString()} ${etRegisterCheckPhone.text.toString()} ")
            }
        }
    }



    companion object{
        private const val TAG = "MainActivity"

    }
    override fun getSubViewModel(): Class<LoginViewModel> = LoginViewModel::class.java
}