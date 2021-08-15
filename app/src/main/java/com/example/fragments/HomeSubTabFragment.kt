package com.example.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adapters.SubTabHomeAdapter
import com.example.base.BaseFragment
import com.example.sobdemo.R
import com.example.sobdemo.databinding.TestFragmentLayoutBinding
import com.example.viewmodels.HomeViewModel

/**
 * Home Tab下的子item
 */
class HomeSubTabFragment : BaseFragment<HomeViewModel>() {

    var mCurrentTabId = ""
    private lateinit var mBinding: TestFragmentLayoutBinding
    private lateinit var mHomeSubTabAdapter: SubTabHomeAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.test_fragment_layout, container, false)
        mBinding = TestFragmentLayoutBinding.bind(rootView)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        initEvent()
    }

    override fun initDataEvent() {
        super.initDataEvent()

        mHomeSubTabAdapter = SubTabHomeAdapter()
        mViewModel.apply {
            tabListLivedata.observe(viewLifecycleOwner, Observer {
                if (it.success) {
                    if (it.data.list.isNotEmpty()) {
                        mHomeSubTabAdapter.setData(it.data.list)
                        mBinding.rvHomeTabSub.adapter = mHomeSubTabAdapter
                    }
                }
            })

            //获取tab下的数据
            getHomeTabSubData(mCurrentTabId, "1")
        }

        mBinding.apply {
            rvHomeTabSub.layoutManager = LinearLayoutManager(context)
        }
    }

     fun initEvent() {

        mHomeSubTabAdapter = SubTabHomeAdapter()
        mViewModel.apply {
            tabListLivedata.observe(viewLifecycleOwner, Observer {
                if (it.success) {
                    if (it.data.list.isNotEmpty()) {
                        mHomeSubTabAdapter.setData(it.data.list)
                        mBinding.rvHomeTabSub.adapter = mHomeSubTabAdapter
                    }
                }
            })

            //获取tab下的数据
            getHomeTabSubData(mCurrentTabId, "1")
        }

        mBinding.apply {
            rvHomeTabSub.layoutManager = LinearLayoutManager(context)
        }

    }


    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)

        Log.d(TAG,"isVisibleToUser  -->  ${isVisibleToUser}")

    }

    companion object {
        private const val TAG = "TestFragment"
        fun getInstance(id: String): HomeSubTabFragment {
            Log.d(TAG, "rece title --> $id")
            val homeSubTabFragment = HomeSubTabFragment()
            homeSubTabFragment.mCurrentTabId = id
            return homeSubTabFragment
        }
    }

    override fun getSubViewModel() = HomeViewModel::class.java

    override fun isDarkBarFont(): Boolean {
        return true
    }

}