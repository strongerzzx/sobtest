package com.example.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adapters.MoYuHotAdapter
import com.example.base.BaseFragment
import com.example.sobdemo.R
import com.example.sobdemo.databinding.MoYuFragmentLayoutBinding
import com.example.viewmodels.ArticleViewModel
import com.example.viewmodels.MoYuViewModel

class MoYuFragment : BaseFragment<MoYuViewModel>() {

    private lateinit var mBinding: MoYuFragmentLayoutBinding
    private lateinit var mHotAdapter: MoYuHotAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.mo_yu_fragment_layout, container, false)
        mBinding = MoYuFragmentLayoutBinding.bind(inflate)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initEvent()
    }

    private fun initEvent() {
        mHotAdapter = MoYuHotAdapter()

        mBinding.apply {
            moYuTopLayout.ivCommonBack.visibility = View.INVISIBLE
            moYuTopLayout.tvCommomTitle.text = "摸鱼"

            rvMoYu.layoutManager = LinearLayoutManager(context)
            rvMoYu.adapter = mHotAdapter

        }

        mViewModel.apply {
            //动态 -- >
            moYouHotListLiveData.observe(viewLifecycleOwner, Observer {
                if (it.success && it.data.isNotEmpty()) {
                    mHotAdapter.setData(it.data)
                }
            })

            val random = (15..20).random()
            mViewModel.getHotMoYuList(random)
        }
    }

    override fun getSubViewModel() = MoYuViewModel::class.java

    override fun isDarkBarFont() = true
}