package com.example.fragments

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import beans.requestbeans.SendSmsVo
import com.bumptech.glide.Glide
import com.example.base.BaseFragment
import com.example.commonparams.CommonParms
import com.example.sobdemo.MainActivity
import com.example.sobdemo.R
import com.example.sobdemo.databinding.CheckSmsFragmentLayoutBinding
import com.example.utils.MmkvUtil
import com.example.viewmodels.UserViewModel

class CheckSmsFragment : BaseFragment<UserViewModel>() {

    private lateinit var mBinding: CheckSmsFragmentLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.check_sms_fragment_layout, container, false)
        mBinding = CheckSmsFragmentLayoutBinding.bind(rootView)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initEvent()

    }

    private fun initEvent() {
        getImmersionBar().init()

        mViewModel.loadCheckCodePic()

        //加载验证码
        mViewModel.apply {
            checkPhoneCodePic.observe(viewLifecycleOwner, {
                Glide.with(this@CheckSmsFragment)
                    .load(it)
                    .into(mBinding.ivYzmCheckPic)
            })

            //获取手机短信验证码
            getForgetPasswordPhoneCode.observe(viewLifecycleOwner, {
                it?.let {
                    if (it.success) {
                        mBinding.tvCountDown.startCountDown()
                        mBinding.tvCountDown.isEnabled = false
                    }
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT)
                        .show()
                }
            })


            //校验smscode
            verifySmsCode.observe(viewLifecycleOwner, {
                it?.let {
                    if (it.success) {
                        //下一步 --> 跳转到输入新密码
                        findNavController().navigate(R.id.action_checkSmsFragment_to_getForgetpswFragment)
                    }
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            })

        }


        mBinding.apply {
            commonLayoutTop.tvCommomTitle.text = "忘记密码"
            commonLayoutTop.ivCommonBack.setOnClickListener {
                startActivity(Intent(context, MainActivity::class.java))
            }

            //获取手机短信验证码
            tvCountDown.setOnClickListener {
                mViewModel.getForetCheckCodeByPhone(
                    MmkvUtil.getString(CommonParms.COOKIE_KEY),
                    SendSmsVo(etForgetPhoneNum.text.toString(), etForgetCheckCode.text.toString())
                )
            }

            ivYzmCheckPic.setOnClickListener {
                mViewModel.loadCheckCodePic()
            }


            //倒计时结束
            tvCountDown.setmOnCountDownOverListener {
                mBinding.tvCountDown.isEnabled = true
                tvCountDown.text = "点击获取短信验证码"
            }

            //校验短信验证码
            btnVerifyPhoneCode.setOnClickListener {

                if (TextUtils.isEmpty(etForgetPhoneNum.text.toString())
                    || TextUtils.isEmpty(etForgetInputSmsCode.text.toString())
                ) {
                    Toast.makeText(context, "账号或密码不能为空", Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                }

                mViewModel.doVerifyPhoneCode(
                    MmkvUtil.getString(CommonParms.COOKIE_KEY),
                    etForgetPhoneNum.text.toString(),
                    etForgetInputSmsCode.text.toString()
                )
            }
        }
    }

    override fun getSubViewModel() = UserViewModel::class.java

    override fun isDarkBarFont() = true
}