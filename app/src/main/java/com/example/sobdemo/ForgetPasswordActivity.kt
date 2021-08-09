package com.example.sobdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import base.BaseActivity
import beans.requestbeans.SendSmsVo
import com.bumptech.glide.Glide
import com.example.beans.requestbeans.UserInfo
import com.example.commonparams.CommonParms
import com.example.sobdemo.databinding.ActivityForgetPasswordBinding
import com.example.utils.MmkvUtil
import com.example.viewmodels.UserViewModel

class ForgetPasswordActivity : BaseActivity<UserViewModel>() {

    private lateinit var mBinding: ActivityForgetPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityForgetPasswordBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        initEvent()
    }

    private fun initEvent() {
        getImmersionBar().init()

        mViewModel.loadCheckCodePic()

        //加载验证码
        mViewModel.apply {
            checkPhoneCodePic.observe(this@ForgetPasswordActivity, {
                Glide.with(this@ForgetPasswordActivity)
                    .load(it)
                    .into(mBinding.ivYzmCheckPic)
            })

            //获取手机短信验证码
            getForgetPasswordPhoneCode.observe(this@ForgetPasswordActivity, {
                it?.let {
                    //如果获取成功 --> 隐藏图灵验证码 --> 显示倒计时 --> 进入到填写新的密码界面
                    if (it.success) {
                        mBinding.tvCountDown.visibility = View.VISIBLE
                        mBinding.tvCountDown.startCountDown()
                    }else{
                        mBinding.tvCountDown.visibility = View.INVISIBLE
                    }
                    Toast.makeText(this@ForgetPasswordActivity, it.message, Toast.LENGTH_SHORT).show()
                }
            })

        }


        mBinding.apply {
            commonLayoutTop.tvCommomTitle.text = "忘记密码"
            commonLayoutTop.ivCommonBack.setOnClickListener {
                finish()
            }


            btnGetPhoneCheckCode.setOnClickListener {
                mViewModel.getForetCheckCodeByPhone(
                    MmkvUtil.getString(CommonParms.COOKIE_KEY),
                    SendSmsVo(etForgetPhoneNum.text.toString(), etForgetCheckCode.text.toString())
                )
            }

            ivYzmCheckPic.setOnClickListener {
                mViewModel.loadCheckCodePic()
            }

        }


    }

    override fun getSubViewModel(): Class<UserViewModel> = UserViewModel::class.java

    override fun isDarkBarFont() = true
}