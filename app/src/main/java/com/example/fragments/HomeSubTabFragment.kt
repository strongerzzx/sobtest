package com.example.fragments

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.adapters.SubTabHomeAdapter
import com.example.base.BaseFragment
import com.example.sobdemo.R
import com.example.sobdemo.TabContentActivity
import com.example.sobdemo.databinding.TestFragmentLayoutBinding
import com.example.viewmodels.HomeViewModel
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader

/**
 * Home Tab下的子item
 */
class HomeSubTabFragment : BaseFragment<HomeViewModel>() {

    var mCurrentTabId = ""
    var mIsSlideBottom = false
    private var mCurrentPage = DEFAULT_PAGE
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

    }

    override fun initLazyDataEvent() {
        super.initLazyDataEvent()

        mHomeSubTabAdapter = SubTabHomeAdapter()

        mBinding.apply {

            tabRefreshTabHome.setRefreshFooter(ClassicsFooter(context))
            val linearLayoutManager = LinearLayoutManager(context)
            rvHomeTabSub.layoutManager = linearLayoutManager
            rvHomeTabSub.adapter = mHomeSubTabAdapter
            //判断rv是否滑到了最底部 --> 如果是则加载更多
            rvHomeTabSub.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (dy < 0) return

                    val lastVisiItemPos = linearLayoutManager.findLastVisibleItemPosition()
                    if (lastVisiItemPos == mHomeSubTabAdapter.itemCount - 1) {
                        //加载更多
                        mIsSlideBottom = true
                    }
                }
            })

            tabRefreshTabHome.setOnLoadMoreListener {
                if (mIsSlideBottom) {
                    mViewModel.getHomeTabSubData(mCurrentTabId, ++mCurrentPage)
                    mBinding.tabRefreshTabHome.finishLoadMore()
                    mIsSlideBottom = false
                }

                Log.d(TAG, "加载更多")
            }
        }

        mViewModel.apply {
            tabListLivedata.observe(viewLifecycleOwner, Observer {
                if (it.success) {
                    if (it.data.list.isNotEmpty()) {
                        mHomeSubTabAdapter.setData(it.data.list)
                    }
                }
            })

            //获取tab下的数据
            getHomeTabSubData(mCurrentTabId, mCurrentPage)
        }


        mHomeSubTabAdapter.setOnTabItemContentClickListener {

            val intent = Intent(context, TabContentActivity::class.java)
            intent.putExtra("contentId", it)
            startActivity(intent)
            Log.d(TAG, "tab item content id --> $it")

        }

    }

    companion object {
        private const val TAG = "TestFragment"
        private const val DEFAULT_PAGE = 1

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