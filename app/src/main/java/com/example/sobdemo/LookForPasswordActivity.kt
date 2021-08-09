package com.example.sobdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import base.BaseActivity
import com.example.sobdemo.databinding.ActivityLookForPasswordBinding
import com.example.viewmodels.UserViewModel

class LookForPasswordActivity : AppCompatActivity() {

    private lateinit var mBiding: ActivityLookForPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBiding = ActivityLookForPasswordBinding.inflate(layoutInflater)
        setContentView(mBiding.root)


        initEvent()

    }

    private fun initEvent() {
        //TODO:暂时只展示一个界面

    }

}