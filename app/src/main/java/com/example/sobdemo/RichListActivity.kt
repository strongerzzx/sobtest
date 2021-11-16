package com.example.sobdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import base.BaseActivity
import com.example.adapters.RichListAdapter
import com.example.sobdemo.databinding.ActivityRichListBinding
import com.example.viewmodels.UserViewModel
import com.gyf.immersionbar.ImmersionBar

class RichListActivity : BaseActivity<UserViewModel>() {

    private lateinit var mBinding: ActivityRichListBinding
    private lateinit var mRichAdapter: RichListAdapter
    private var mRichCount = DEFAULT_RICH_COUNT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityRichListBinding.inflate(layoutInflater)
        setContentView(mBinding.root)


        ImmersionBar.with(this)
                .statusBarView(mBinding.viewBarFit)
                .statusBarDarkFont(false)
                .init()

        initEvent()

    }

    private fun initEvent() {

        mRichAdapter = RichListAdapter()
        mBinding.rvRichList.layoutManager = LinearLayoutManager(this)
        mBinding.rvRichList.adapter = mRichAdapter

        //富豪榜
        mViewModel.richListLivedata.observe(this, Observer {
            mRichAdapter.setData(it.data)
        })
        mViewModel.getRichList(30)

    }


    companion object {
        private const val TAG = "RichListActivity"
        private const val DEFAULT_RICH_COUNT = 20
    }

    override fun getSubViewModel(): Class<UserViewModel> = UserViewModel::class.java

    override fun isDarkBarFont() = false
}