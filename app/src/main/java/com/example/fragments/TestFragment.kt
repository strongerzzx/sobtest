package com.example.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.sobdemo.R
import com.example.sobdemo.databinding.TestFragmentLayoutBinding

class TestFragment : Fragment() {

    private lateinit var mBinding: TestFragmentLayoutBinding

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


    companion object {

        private const val TAG = "TestFragment"

        fun getInstance(id: String): TestFragment {
            return TestFragment()
        }
    }

}