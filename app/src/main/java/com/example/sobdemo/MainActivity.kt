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

        Log.d(TAG,"2222222222")
        Log.d(TAG,"3333333333")
    }

    private fun initEvent() {

        mViewModel.getRandomPic()

        mBinding.apply {
            var randomNum = UUID.randomUUID()

            Glide.with(this@MainActivity)
                    .load(BaseRetrofit.BASE_URL + "/uc/ut/captcha?code=${randomNum}")
                    .into(ivRegisterCheckPic)

            ivRegisterCheckPic.setOnClickListener {
                randomNum = UUID.randomUUID()
                Glide.with(this@MainActivity)
                        .load(BaseRetrofit.BASE_URL + "/uc/ut/captcha?code=${randomNum}")
                        .into(ivRegisterCheckPic)
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