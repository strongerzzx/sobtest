package com.example.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.base.BaseFragment
import com.example.beans.userinfo.UserInfo
import com.example.commonparams.CommonParms
import com.example.sobdemo.R
import com.example.sobdemo.databinding.MineFragmentLayoutBinding
import com.example.utils.MmkvUtil
import com.example.viewmodels.UserViewModel
import com.google.gson.Gson

class MineFragment : BaseFragment<UserViewModel>() {

    private lateinit var mBinding: MineFragmentLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.mine_fragment_layout, container, false)
        mBinding = MineFragmentLayoutBinding.bind(inflate)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initEvent()
    }

    private fun initEvent() {
        //基本信息
        setupBasicUser()

        mViewModel.apply {

            unUserReadMsg.observe(viewLifecycleOwner, Observer {
                if (it.success) {
                    //TODO:更新未读信息的个数
                    if (it.data.momentCommentCount >= 0) {
                        mBinding.tvUnRadMsgCount.visibility = View.VISIBLE
                        mBinding.tvUnRadMsgCount.text = it.data.momentCommentCount.toString()
                    }
                }
            })


            getUnReadMessage()
        }
    }

    private fun setupBasicUser() {
        val gson = Gson()
        val userinfoLocal = MmkvUtil.getString(CommonParms.BASIC_USER_INFO)
        val userInfo = gson.fromJson<UserInfo>(userinfoLocal, UserInfo::class.java)
        val glideOption = RequestOptions.bitmapTransform(CircleCrop())

        Glide.with(this)
            .load(userInfo.data.avatar)
            .apply(glideOption)
            .into(mBinding.ivRoundAvter)

        mBinding.tvNickName.text = userInfo.data.nickname
        mBinding.tvUserId.text = userInfo.data.userId
        mBinding.tvUserVip.visibility = if (userInfo.data.vip) {
            View.VISIBLE
        } else {
            View.GONE
        }


        Log.d(TAG, "userinfoStr  --> $userinfoLocal")
    }

    override fun getSubViewModel() = UserViewModel::class.java

    override fun isDarkBarFont() = true

    companion object {
        private const val TAG = "MineFragment"
    }

}