package com.example.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.base.BaseFragment
import com.example.beans.requestbeans.UserInfo
import com.example.commonparams.CommonParms
import com.example.sobdemo.MainActivity
import com.example.sobdemo.R
import com.example.sobdemo.databinding.CheckSmsFragmentLayoutBinding
import com.example.sobdemo.databinding.GetForgetPswFragmentLayoutBinding
import com.example.utils.MmkvUtil
import com.example.viewmodels.UserViewModel
import com.google.gson.JsonArray
import com.google.gson.JsonParser

class GetForgetpswFragment : BaseFragment<UserViewModel>() {

    private lateinit var mBinding: GetForgetPswFragmentLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.get_forget_psw_fragment_layout, container, false)
        mBinding = GetForgetPswFragmentLayoutBinding.bind(rootView)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        mViewModel.apply {
            lookforForgetpassword.observe(viewLifecycleOwner, {
                it?.let {
                    if (it.success){
                        //TODO:修改成功后
                    }
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            })

        }

        mBinding.apply {
            setNewForgetPasswordTop.tvCommomTitle.text = "找回密码"
            setNewForgetPasswordTop.ivCommonBack.setOnClickListener {
                startActivity(Intent(context, MainActivity::class.java))
            }

            //找回密码
            btnComfirmSetFoget.setOnClickListener {
                mViewModel.getForgetPassword(
                    MmkvUtil.getString(CommonParms.COOKIE_KEY),
                    etSetForgetSmsCode.text.toString(),
                    UserInfo(
                        etSetForgetPhoneNum.text.toString(),
                        etSetForgetPassword.text.toString()
                    )
                )
            }

            //TODO:
        }



    }

    override fun getSubViewModel() = UserViewModel::class.java

    override fun isDarkBarFont() = true
}